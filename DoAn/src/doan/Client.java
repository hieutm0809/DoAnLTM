package doan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import doan.Connection.DTO.infoUser;
import GUI.GUI;
import GUI.LoginView;
import GUI.RegisterView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import doan.Connection.DTO.infoGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

class SendMessage implements Runnable {

    private BufferedWriter out;
    private Socket socket;

    public SendMessage(Socket s, BufferedWriter o) {
        this.socket = s;
        this.out = o;
    }

    public void Login() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String id = Client.guiLogin.userText.getText();
            String pass = new String(Client.guiLogin.passwordText.getPassword());
            System.out.println("Client send: login#" + id + "#" + pass + '\n');
            out.write("login#" + id + "#" + pass + '\n');
            out.flush();
        } catch (IOException e) {
        }
    }

    public void run() {
        switch (Client.status) {
            case "login": {
                Login();
            }
            break;
            case "chat": {
           // mode
            }
        }

//        try {
//            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//            String data = "";//Client.gui.txtContent.getText();
//            String id = "";//Client.gui.pnlChatTo.getText();
//            System.out.println("Client send:" + id + "#" +data + '\n');
//            out.write(id + "#" +data + '\n');
//            out.flush();
//            
//            //Client.gui.txtContent.setText("");
//            //Client.gui.txtAChat.append(Client.name + ": " +data + '\n');
//            if (data.equals("bye")) {
//                System.out.println("Client closed connection");
//                out.close();
//                socket.close();
//                Client.executor.shutdownNow();
//            }
//        } catch (IOException e) {
//        }
    }
    
}

class ReceiveMessage implements Runnable {

    private BufferedReader in;
    private Socket socket;

    public ReceiveMessage(Socket s, BufferedReader i) {
        this.socket = s;
        this.in = i;
    }

    public void Process(String line) throws IOException {
        if (!line.contains("#")) {
            System.out.println(line);
        } else {
            String[] parts = line.split("#");

            switch (parts[0]) {
                case "system": {
                    switch (parts[1]) {
                        case "login": {
                            switch (parts[2]) {
                                case "success": {
                                    Client.gui = new GUI(); 
                                    Client.status = "chat";
                                    Client.gui.name = parts[3];
                                    Client.guiLogin.setVisible(false);
                                    Client.gui.displayGUI();
                                    Client.gui.setVisible(true);                                    
                                }
                                break;
                                default: {
                                    Client.guiLogin.alert(parts[2]);
                                }
                            }
                        }
                        break;
                        case "friendlist": {
                            if(parts.length == 3){
                                String arr = parts[2];
                                Client.gui.FriendList(arr);
                            }
                        }
                        break;
                        case "groupchat": {
                            if(parts.length == 3){
                                String arr = parts[2];
                                Client.gui.GroupChat(arr);
                            }
                        }
                        break;
                    }
                }
                break;

                default: {
                    //Client user2: TheKhanh(nguoinhan)#noidungchat
                    //======= Client TK: user2(nguoigui)#noidungchat
//                    if(Client.gui != null)
//                        Client.gui.txtAChat.append(parts[0]+ ": " +parts[1]+"\n");

                }
            }
        }
    }

    public void run() {
        try {
            while (true) {
                String data = in.readLine();
                Process(data);
                System.out.println("Receive: " + data);

            }
        } catch (IOException e) {
        }
    }
}

public class Client {

    private static String host = "localhost";
    private static int port = 1234;
    private static Socket socket;
    static ExecutorService executor;
    public static GUI gui;
    static LoginView guiLogin;
    static RegisterView guiRegister;
    static String status;
    //static String name;

    private static BufferedWriter out;
    private static BufferedReader in;

    private static SendMessage send;
    private static ReceiveMessage recv;
    
    public static int id;
    
    public static void executeSendMessage() {
        executor.execute(send);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        socket = new Socket(host, port);
        System.out.println("Client connected");
        status = "login";

        guiLogin = new LoginView();
        guiLogin.displayGUI();
        guiLogin.setLocationRelativeTo(null);
        guiLogin.setVisible(true);

        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        send = new SendMessage(socket, out);
        recv = new ReceiveMessage(socket, in);
        executor = Executors.newFixedThreadPool(2);
        executor.execute(recv);
    }
}

//JLabel lb = new JLabel("Groups", JLabel.LEFT);
//        lb.setBounds(0, 300, 240, 30);
//		
//		 JLabel lb = new JLabel("Friends", JLabel.LEFT);
//        lb.setBounds(0, 0, 240, 30);
