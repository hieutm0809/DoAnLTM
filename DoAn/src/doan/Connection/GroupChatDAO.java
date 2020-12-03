/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import doan.Connection.GroupChatDTO;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class GroupChatDAO {

    public ArrayList showGroupChat() throws SQLException, SQLException, SQLException, JsonProcessingException {
        MySQLConnect ConnectData = new MySQLConnect();
        ArrayList dsgroupchat = new ArrayList<GroupChatDTO>();

        String qry = "select * from groupchat";
        ConnectData.st = ConnectData.conn.createStatement();
        ConnectData.rs = ConnectData.st.executeQuery(qry);
        
        while (ConnectData.rs.next()) {
            GroupChatDTO groupchat = new GroupChatDTO();
            groupchat.setGroupID(ConnectData.rs.getInt(1));
            groupchat.setGroupname(ConnectData.rs.getString(2));
            ObjectMapper mapper = new ObjectMapper();
            String arr = ConnectData.rs.getString(3);
            int[] pp1 = mapper.readValue(arr, int[].class);
            groupchat.setMemberlist(pp1);
            dsgroupchat.add(groupchat);
        }
        ConnectData.MySQLDisconnect();
        return dsgroupchat;
    }
}
