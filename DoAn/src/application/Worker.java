package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import DTO.infoGroup;
import BUS.FriendListBUS;
import BUS.UserBUS;
import DTO.UserDTO;
import DTO.FriendListDTO;
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
import DTO.infoUser;
import BUS.GroupChatBUS;
import DTO.GroupChatDTO;
import BUS.MessageFriendBUS;
import DTO.MessageFriendDTO;
import BUS.MessageGroupBUS;
import BUS.statisticalUser;
import DTO.MessageGroupDTO;
import DTO.contentMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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

    public void sendToOne(int id, String mess) throws IOException, SQLException {
        for (Worker worker : Server.workers) {
            if (worker.myName == id) {
                worker.out.write(this.myName + "#" + mess + '\n');
                worker.out.flush();
                System.out.println("Server write: " + mess + " to " + worker.myName);
                MessageFriendBUS messagefriendBUS = new MessageFriendBUS();
                MessageFriendDTO messagefriend = new MessageFriendDTO();
                messagefriend = messagefriendBUS.getMessageByParticipantsID(this.myName, worker.myName);
                if (messagefriend.getParticipant1() == 0) {
                    insertMessageFriend(this.myName, worker.myName, mess);
                    break;
                } else {
                    updateMessageFriend(this.myName, worker.myName, mess);
                    break;
                }

            }
        }
    }

    public void sendToGroup(int groupID, String mess) throws SQLException, JsonProcessingException, IOException {
        GroupChatBUS groupchatBUS = new GroupChatBUS();
        GroupChatDTO groupchat = new GroupChatDTO();
        groupchat = groupchatBUS.getMemberListByGroupID(groupID);
        int[] arr = groupchat.getMemberlist();
        for (int i = 0; i < arr.length; i++) {
            if (this.myName == arr[i]) {
                continue;
            }
            for (Worker worker : Server.workers) {
                if (worker.myName == arr[i]) {
                    worker.out.write(this.myName + "#" + mess + '\n');
                    worker.out.flush();
                    System.out.println("Server write: " + mess + " to " + worker.myName);
                    MessageGroupBUS messagegroupBUS = new MessageGroupBUS();
                    MessageGroupDTO messagegroup = new MessageGroupDTO();
                    messagegroup = messagegroupBUS.showMessageGroup(groupID);
                    if (messagegroup.getGroupID() == 0) {
                        insertMessageGroup(groupID, this.myName, mess);
                        break;
                    } else {
                        updateMessageGroup(groupID, this.myName, mess);
                        break;
                    }
                }
            }
        }
    }

    public void updateMessageFriend(int participant1, int participant2, String mess) throws SQLException, JsonProcessingException {
        ArrayList arrMess = new ArrayList<contentMessage>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        MessageFriendBUS messagefriendBUS = new MessageFriendBUS();
        MessageFriendDTO messagefriend = new MessageFriendDTO();
        messagefriend = messagefriendBUS.getMessageByParticipantsID(participant1, participant2);
        contentMessage[] respone = new Gson().fromJson(messagefriend.getContent(), contentMessage[].class);
        for (contentMessage s : respone) {
            contentMessage contentLoad = new contentMessage(s.getFrom(), s.getTime(), s.getContent());
            arrMess.add(contentLoad);
        }
        contentMessage content = new contentMessage(participant1, dateFormat.format(date), mess);
        arrMess.add(content);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(arrMess).replace("\"", "\\\"");
        MessageFriendDTO update = new MessageFriendDTO(participant1, participant2, JSONObject);
        messagefriendBUS.updateMessageFriend(update);
    }

    public void insertMessageFriend(int participant1, int participant2, String mess) throws SQLException {
        ArrayList arrMess = new ArrayList<contentMessage>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        contentMessage content = new contentMessage(participant1, dateFormat.format(date), mess);
        arrMess.add(content);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(arrMess).replace("\"", "\\\"");
        MessageFriendBUS messagefriendBUS = new MessageFriendBUS();
        MessageFriendDTO insert = new MessageFriendDTO(participant1, participant2, JSONObject);
        messagefriendBUS.addMessageFriend(insert);
    }

    public void updateMessageGroup(int groupID, int sender, String mess) throws SQLException, JsonProcessingException {
        ArrayList arrMess = new ArrayList<contentMessage>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        MessageGroupBUS messagegroupBUS = new MessageGroupBUS();
        MessageGroupDTO messagegroup = new MessageGroupDTO();
        messagegroup = messagegroupBUS.showMessageGroup(groupID);
        contentMessage[] respone = new Gson().fromJson(messagegroup.getContent(), contentMessage[].class);
        for (contentMessage s : respone) {
            contentMessage contentLoad = new contentMessage(s.getFrom(), s.getTime(), s.getContent());
            arrMess.add(contentLoad);
        }
        contentMessage content = new contentMessage(sender, dateFormat.format(date), mess);
        arrMess.add(content);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(arrMess).replace("\"", "\\\"");
        MessageGroupDTO update = new MessageGroupDTO(groupID, JSONObject);
        messagegroupBUS.updateMessageGroup(update);
    }

    public void insertMessageGroup(int groupID, int sender, String mess) throws SQLException {
        ArrayList arrMess = new ArrayList<contentMessage>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        contentMessage content = new contentMessage(sender, dateFormat.format(date), mess);
        arrMess.add(content);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJson(arrMess).replace("\"", "\\\"");
        MessageGroupBUS messagegroupBUS = new MessageGroupBUS();
        MessageGroupDTO insert = new MessageGroupDTO(groupID, JSONObject);
        messagegroupBUS.addMessageGroup(insert);
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
        messagefriend = messagefriendBUS.getMessageByParticipantsID(participant1, participant2);
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
                case "group": {
                    sendToGroup(Integer.parseInt(parts[1]), parts[2]);
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
            //friendList();
            while (true) {
                input = in.readLine();
                System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + myName);
                Process(input);
                Scanner sc = new Scanner(System.in);
                String com = sc.nextLine(); // sumUser
                if(com.equals("sumUser")) {
                        System.out.println("Tổng số User đang online: " + Server.sum);
                        statisticalUser statistical = new statisticalUser();
                        statistical.statisticalUser();
                        break;
                }
            }
//            in.close();
//            out.close();
//            socket.close();
//            Server.workers.remove(this);
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
