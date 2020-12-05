package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import GUI.GUI;
import GUI.LoginView;
import GUI.RegisterView;
import com.google.gson.Gson;
import DTO.contentMessageFriend;
import DTO.contentMessageGroup;
import BUS.UserBUS;
import DTO.UserDTO;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            try {             
                Chat();
            } catch (IOException ex) {
                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            break;
            case "system": {
                try {
                    out.write(Client.command + '\n');
                    out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                Client.status = "chat";
                Client.command = "";
            }
            break;
        }
    }

    public void Chat() throws IOException {
        String data = Client.gui.c_input.getText();
        if(data.equals("")){
            return;
        }
        if(Client.chatMode.equals("oneToOne")){
            out.write(Client.chatTo + "#" + data + '\n');
        }else{
            out.write("group#" +Client.chatTo + "#" + data + '\n');           
        }
        out.flush();
        Client.gui.c_input.setText("");
        Client.gui.c_display.append(Client.name + ": " + data + '\n');
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
                                    Client.name = parts[3];
                                    Client.guiLogin.setVisible(false);
                                    Client.gui.displayGUI();
                                    Client.gui.setDisableInput();
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
                            if (parts.length == 3) {
                                String arr = parts[2];
                                Client.gui.FriendList(arr);
                            }
                        }
                        break;
                        case "groupchat": {
                            if (parts.length == 3) {
                                String arr = parts[2];
                                Client.gui.GroupChat(arr);
                            }
                        }
                        break;
                        case "showMessageFriend": {
                            if(parts.length == 3) {
                                String arr = parts[2];
                                contentMessageFriend[] respone = new Gson().fromJson(arr, contentMessageFriend[].class);
                                for (contentMessageFriend s : respone) {
                                    UserBUS bususer = new UserBUS();
                                    bususer.docDSuser();
                                    UserDTO user = new UserDTO();
                                    user = bususer.takeInfoUserByID(s.getFrom());
                                    Client.gui.c_display.append(user.getFullname() + ": " + s.getContent()+'\n');
                                }
                            }
                        }
                        break;
                        case "showMessageGroup": {
                            if(parts.length == 3) {
                                String arr = parts[2];
                                contentMessageGroup[] respone = new Gson().fromJson(arr, contentMessageGroup[].class);
                                for (contentMessageGroup s : respone) {
                                    UserBUS bususer = new UserBUS();
                                    bususer.docDSuser();
                                    UserDTO user = new UserDTO();
                                    user = bususer.takeInfoUserByID(s.getFrom());
                                    Client.gui.c_display.append(user.getFullname() + ": " + s.getContent()+'\n');
                                }
                            }
                        }
                        break;
                    }
                }
                break;

                default: {
                    UserBUS bususer = new UserBUS();
                    bususer.docDSuser();
                    UserDTO user = new UserDTO();
                    user = bususer.takeInfoUserByID(Integer.parseInt(parts[0]));
                    Client.gui.c_display.append(user.getFullname() + ": " +parts[1] +'\n');
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
    static String name;
    public static int chatTo;
    public static String chatMode;
    public static String command;
    private static BufferedWriter out;
    private static BufferedReader in;

    private static SendMessage send;
    private static ReceiveMessage recv;

    public static int id;

    public static void executeSendMessage() {
        executor.execute(send);
    }

    public static void systemSendMessage(String command) {
        Client.command = command;
        Client.status = "system";
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
