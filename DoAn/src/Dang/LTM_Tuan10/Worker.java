package LTM_Tuan10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Worker implements Runnable {

    private String myName;
    private Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Worker(Socket s) throws IOException {
        this.socket = s;
        this.myName = "";
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }
    
    public void sendToOne(String id, String mess ) throws IOException {
        boolean found = false;
        for(Worker worker: Server.workers) {
            if(worker.myName.equals(id)) {
                worker.out.write(this.myName+ "#"+mess + '\n');
                worker.out.flush();
                System.out.println("Server write: " + mess + " to " + worker.myName);
                found = true;
                break;
            }
        }
        if(!found){
            this.out.write("id is not found!" + '\n');
            this.out.flush();
            System.out.println("Server write: id is not found! to " + this.myName);
        }
    }
    
    public void sendToAll(String mess) throws IOException {
        for(Worker worker: Server.workers) {
            if (!this.myName.equals(worker.myName)) {
                worker.out.write(this.myName+ " to all: "+mess + '\n');
                worker.out.flush();
                System.out.println("Server write: " + mess + " to " + worker.myName);
            }
        }
    }
    
    public void systemCommand(String mess) throws IOException {
        this.out.write("system#"+ mess + "\n");
        this.out.flush();
        System.out.println("Send: system#" + mess + " to " + this.myName);
    }
    
    public void Process(String line) throws IOException {
        if(!line.contains("#")){
            this.out.write("Syntax error" + '\n');
            this.out.flush();
            System.out.println("Server write: id is not found! to " + this.myName);
        } else {
            String[] parts = line.split("#");
            switch(parts[0]){
                case "all":{
                    sendToAll(parts[1]);
                } break;
                default: {
                    sendToOne(parts[0],parts[1]);
                }
            }
        }
    }
    
    public void setName() throws IOException{
        boolean duplicate;
        String name = in.readLine();    
        this.myName = name;
        systemCommand("showGUI,"+this.myName);
    }
    
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            String input = "";
            setName();
            sendToAll("user "+ this.myName +" log in to server");
            while (true) {
                input = in.readLine();
                System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + myName);
                if (input.equals("bye")) {
                    break;
                }
                Process(input);
            }
            System.out.println("Closed socket for client " + myName + " " + socket.toString());
            sendToAll("user " + this.myName + " log out from server");
            in.close();
            out.close();
            socket.close();
            Server.workers.remove(this);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
