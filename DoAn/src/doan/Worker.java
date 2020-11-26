package doan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker implements Runnable {

    private String myName;
    private Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Worker(Socket s, String name) throws IOException {
        this.socket = s;
        this.myName = name;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public void run() {
        System.out.println("Client " + socket.toString() + " is accepted");
        try {
            myName = in.readLine();
            sendUnicast(myName, "NAME#" + myName);
            sendBroadcast(myName + " is online");
            String input = "";
            while (true) {
                input = in.readLine();
                System.out.println(" # Client " + myName +" : " + input);
                if (input.equals("bye")) {
                    System.out.println(" # Client " + myName + " is offline"); 
                    break;
                }
                if (sendBroadcast(input) != 0) {
                    out.write("");
                } else {
                    out.write("");
                }
                out.flush();
            }
            //System.out.println("Closed socket for client " + name + " " + socket.toString());
            sendBroadcast(myName + " is offline");
            in.close();
            out.close();
            socket.close();
            removeWorker(myName);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private int sendBroadcast(String msg) {
        try {
            for (Worker worker : Server.workers) {
                if (!myName.equals(worker.myName)) {
                    worker.out.write(msg + '\n');
                    worker.out.flush();
                }
            }
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    private int sendUnicast(String name, String msg) throws IOException {
        try {
            for (Worker worker : Server.workers) {
                if (name.equals(worker.myName)) {
                    worker.out.write(msg + '\n');
                    worker.out.flush();
                    return 0;	// success
                }
            }
        } catch (Exception e) {
            return 2;
        } // exception
        return 1; // client not found
    }

    private boolean removeWorker(String name) {
        try {
            for (Worker worker : Server.workers) {
                if (name.equals(worker.myName)) {
                    Server.workers.remove(worker);
                    break;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
