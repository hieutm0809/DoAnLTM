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
                                    Client.gui.displayGUI();
                                    Client.gui.setVisible(true);
                                    Client.guiLogin.setVisible(false);
                                    Client.status = "chat";
                                }
                                break;
                                default: {
                                    Client.guiLogin.alert(parts[2]);
                                }
                            }
                        }
                        break;
                        case "friendlist":
                            ObjectMapper mapper = new ObjectMapper();
                            String arr = parts[2];
                            infoUser[] respone = new Gson().fromJson(arr, infoUser[].class);
                            for (infoUser s : respone) {
                                System.out.println("Id: " + s.getId());
                                System.out.println("Name: " + s.getFullname());
                                System.out.println("Status: "+ s.isOnline());
                            }
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
    static GUI gui;
    static LoginView guiLogin;
    static RegisterView guiRegister;
    static String status;
    //static String name;

    private static BufferedWriter out;
    private static BufferedReader in;

    private static SendMessage send;
    private static ReceiveMessage recv;

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
