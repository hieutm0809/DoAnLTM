package doan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import doan.testChat;

class SendMessage implements Runnable {

    private BufferedWriter out;
    private Socket socket;

    public SendMessage(Socket s, BufferedWriter o) {
        this.socket = s;
        this.out = o;
    }

    public void run() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String data = Client.gui.txtContent.getText();
            String gr = Client.gui.pnlChatTo.getText();
            System.out.println("Client send:" + gr+ "#" +data + '\n');
            out.write(gr + "#" +data + '\n');
            out.flush();
            
            Client.gui.txtContent.setText("");
            Client.gui.txtAChat.append(Client.name + ": " +data + '\n');
            if (data.equals("bye")) {
                System.out.println("Client closed connection");
                out.close();
                socket.close();
                Client.executor.shutdownNow();
            }
        } catch (IOException e) {
        }
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
        if(!line.contains("#")){
            System.out.println(line);
        } else {
            String[] parts = line.split("#");
            switch (parts[0]) {
                case "system": {
                    if (parts[1].contains("showGUI")) {
                        String[] temp = parts[1].split(",");
                        Client.gui = new testChat(temp[1]);
                    }
                }
                break;
                default: {
                    if(Client.gui != null)
                        Client.gui.txtAChat.append(parts[0]+ ": " +parts[1]+"\n");

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
    static testChat gui;
    static String name;

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
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        send = new SendMessage(socket, out);
        recv = new ReceiveMessage(socket, in);
        executor = Executors.newFixedThreadPool(2);
        executor.execute(recv);
        out.write(name+"\n");
        out.flush();
    }
}
