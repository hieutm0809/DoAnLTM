package doan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import doan.Connection.DTO.infoGroup;
import doan.Connection.FriendListBUS;
import doan.Connection.UserBUS;
import doan.Connection.UserDTO;
import doan.Connection.FriendListDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import doan.Connection.DTO.infoUser;
import doan.Connection.GroupChatBUS;
import doan.Connection.GroupChatDTO;
import doan.Connection.MessageFriendBUS;
import doan.Connection.MessageFriendDTO;
import doan.Connection.MessageGroupBUS;
import doan.Connection.MessageGroupDTO;

public class Worker implements Runnable {

    private int myName;
    private Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Worker(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public void sendToOne(int id, String mess) throws IOException {
        for (Worker worker : Server.workers) {
            if (worker.myName == id) {
                worker.out.write(this.myName + "#" + mess + '\n');
                worker.out.flush();
                System.out.println("Server write: " + mess + " to " + worker.myName);
                //save message to DB
                break;
            }
        }
    }

    public void sendToAll(String mess) throws IOException {
        for (Worker worker : Server.workers) {
            if (!(this.myName == worker.myName)) {
                worker.out.write(this.myName + " to all: " + mess + '\n');
                worker.out.flush();
                System.out.println("Server write: " + mess + " to " + worker.myName);
            }
        }
    }

    public void systemCommand(String mess) throws IOException {
        this.out.write("system#" + mess + "\n");
        this.out.flush();
        System.out.println("Send: system#" + mess + " to " + this.myName);
    }

//    public void setName(String name) throws IOException{    
//        this.myName = name;
//        //systemCommand("showGUI,"+this.myName);
//    }
    public void login(String userName, String password) throws IOException, SQLException {
        UserBUS bususer = new UserBUS();
        bususer.docDSuser();
        UserDTO user = new UserDTO();
        user = bususer.Tim(userName);

        if (user == null) {
            systemCommand("login#Tài khoản không tồn tại");
        } else if (userName.equals(user.getUsername()) && password.equals(user.getPassword())) {
            systemCommand("login#success#" + user.getFullname());
            this.myName = user.getId();
            showFriendList();
            showGroupChat();
        } else {
            systemCommand("login#Mật khẩu không đúng");
        }

    }

    public void showFriendList() throws SQLException, JsonProcessingException, IOException {
        UserBUS bususer = new UserBUS();
        UserDTO user = new UserDTO();
        FriendListBUS friendlistBUS = new FriendListBUS();
        FriendListDTO friendlist = friendlistBUS.findFriendListByID(this.myName);

        int[] arr = friendlist.getUsername();
        if (arr == null) {
            systemCommand("friendlist#");
            return;
        }
        ArrayList dsfriend = new ArrayList<infoUser>();
        for (int i = 0; i < arr.length; i++) {
            user = bususer.takeInfoUserByID(arr[i]);
            boolean isOnline = false;
            for (Worker worker : Server.workers) {
                if (arr[i] == worker.myName) {
                    isOnline = true;
                    break;
                }
            }
            infoUser infouser = new infoUser(arr[i], user.getFullname(), isOnline);
            dsfriend.add(infouser);
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(dsfriend);
        System.out.println(JSONObject);
        systemCommand("friendlist#" + JSONObject);
    }

    public void showGroupChat() throws SQLException, JsonProcessingException, IOException {
        GroupChatBUS groupchatBUS = new GroupChatBUS();
        groupchatBUS.showGroupChat();
        ArrayList dsgroup = new ArrayList<infoGroup>();
        for (GroupChatDTO groupchat : groupchatBUS.dsgroupchat) {
            int[] arr = groupchat.getMemberlist();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == this.myName) {
                    infoGroup infogroup = new infoGroup(groupchat.getGroupID(), groupchat.getGroupname());
                    dsgroup.add(infogroup);
                    break;
                }
            }
        }
        if (dsgroup.size() == 0) {
            systemCommand("groupchat#");
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(dsgroup);
        System.out.println(JSONObject);
        systemCommand("groupchat#" + JSONObject);
    }

    public void showMessageFriend(int participant1, int participant2) throws JsonProcessingException, IOException {
        MessageFriendBUS messagefriendBUS = new MessageFriendBUS();
        MessageFriendDTO messagefriend = new MessageFriendDTO();
        messagefriend = messagefriendBUS.showMessageFriend(participant1, participant2);
        if (messagefriend.getContent() == null) {
            systemCommand("showMessageFriend#");
            return;
        }
        systemCommand("showMessageFriend#" + messagefriend.getContent());
    }
    
    public void showMessageGroup(int groupID) throws JsonProcessingException, IOException {
        MessageGroupBUS messagegroupBUS = new MessageGroupBUS();
        MessageGroupDTO messagegroup = new MessageGroupDTO();
        messagegroup = messagegroupBUS.showMessageGroup(groupID);
        if (messagegroup.getContent() == null) {
            systemCommand("showMessageGroup#");
            return;
        }
        systemCommand("showMessageGroup#" + messagegroup.getContent());
    }
    
    public void Process(String line) throws IOException, SQLException {
        if (!line.contains("#")) {
            this.out.write("Syntax error" + '\n');
            this.out.flush();
            System.out.println("Server write: id is not found! to " + this.myName);
        } else {
            String[] parts = line.split("#");
            switch (parts[0]) {
                case "all": {
                    sendToAll(parts[1]);
                }
                break;
                case "login": {
                    login(parts[1], parts[2]);
                }
                break;
                case "showMessageFriend": {
                    showMessageFriend(this.myName, Integer.parseInt(parts[1]));
                }
                break;
                case "showMessageGroup": {
                    showMessageGroup(Integer.parseInt(parts[1]));
                }
                break;
                default: {
                    sendToOne(Integer.parseInt(parts[0]), parts[1]);
                }
            }
        }
    }

    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            String input = "";
            //setName();
            sendToAll("user " + this.myName + " log in to server");
            //friendList();
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
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
