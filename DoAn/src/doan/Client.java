package doan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SendMessage implements Runnable {

    private BufferedWriter out;
    private Socket socket;
    private String myName;

    public SendMessage(Socket s, BufferedWriter o, String myName) {
        this.socket = s;
        this.out = o;
        this.myName = myName;
    }

    public void run() {
        try {
            while (true) {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String data = stdIn.readLine();
                out.write(data + '\n');
                out.flush();
                if (data.equals("bye")) {
                    break;
                }
            }
            System.out.println("CLIENT " + Client.myName + " is offline ");
            out.close();
            socket.close();
            Client.executor.shutdownNow();
        } catch (IOException e) {
        }
    }
}

class ReceiveMessage implements Runnable {

    private BufferedReader in;
    private Socket socket;
    private String myName;

    public ReceiveMessage(Socket s, BufferedReader i, String myName) {
        this.socket = s;
        this.in = i;
        this.myName = myName;
    }

    public void run() {
        try {
            while (true) {
                String data = in.readLine();
                if (data.startsWith("NAME#")) {
                    Client.myName = data.split("#")[1];
                    System.out.println("CLIENT " + myName + " connected");
                    continue;
                }
                if (data.equals("bye")) {
                    break;
                }
                System.out.println("CLIENT " + myName + " received: " + data);
            }
            socket.close();
        } catch (IOException e) {
        }
    }
}

public class Client {

    public static ExecutorService executor;
    private static String host = "localhost";
    private static int port = 1234;
    private static Socket socket;
    public static String myName;

    private static BufferedWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException, InterruptedException {
        socket = new Socket(host, port);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        executor = Executors.newFixedThreadPool(2);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên của bạn: ");
        myName = sc.nextLine();
        out.write(myName);
        out.newLine();
        out.flush();
        SendMessage send = new SendMessage(socket, out, myName);
        ReceiveMessage recv = new ReceiveMessage(socket, in, myName);
        executor.execute(send);
        executor.execute(recv);
    }
}
