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
import static doan.Client.gui;
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
                        case "friendlist": {
                            ObjectMapper mapper = new ObjectMapper();
                            String arr = parts[2];
                            FriendList(arr);
                        }
                        break;
                        case "groupchat": {
                            ObjectMapper mapper = new ObjectMapper();
                            String arr = parts[2];
                            infoGroup[] respone = new Gson().fromJson(arr, infoGroup[].class);
                            for (infoGroup s : respone) {
                                System.out.println("Id: " + s.getGroupID());
                                System.out.println("Name: " + s.getGroupname());
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

    public void FriendList(String arr) {
        infoUser[] respone = new Gson().fromJson(arr, infoUser[].class);
        DefaultListModel model = new DefaultListModel();

        for (infoUser s : respone) {
            System.out.println("Id: " + s.getId());
            System.out.println("Name: " + s.getFullname());
            System.out.println("Status: " + s.isOnline());
            model.addElement(s.getFullname());
        }
        JList list = new JList(model);
        list.setFont(new Font("Open Sans", Font.BOLD, 14));
        list.setBackground(new Color(250, 250, 250));
        list.setFixedCellHeight(50);
        list.setFixedCellWidth(230);

        Color outline = new Color(230, 230, 230);
        Border leftBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, outline);

        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                // rồi viết code bắt sự kiện click ở trong này;
                //ví dụ như này
                /*  if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    JOptionPane.showMessageDialog(null, "Heeloo mina");
                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                }
                 */
            }
        });
        JPanel pn = new JPanel();
        pn.setBounds(0, 100, 250, 620);
        pn.setBackground(new Color(250, 250, 250));
        pn.setBorder(leftBorder);
        pn.add(list);

        Client.gui = new GUI();
        gui.add(pn);
        gui.displayGUI();
        Client.gui.setVisible(true);
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
