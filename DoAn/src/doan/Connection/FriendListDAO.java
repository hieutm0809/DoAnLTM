/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;        

import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class FriendListDAO {
    public FriendListDTO findFriendListByID(int id) throws SQLException,JsonProcessingException {
        MySQLConnect ConnectData = new MySQLConnect();
        String sql = "select username from friendlist where userID = '" +id+"'";
        ConnectData.st = ConnectData.conn.createStatement();
        ConnectData.rs = ConnectData.st.executeQuery(sql);
        FriendListDTO friendlist = new FriendListDTO();
        while (ConnectData.rs.next()) {
            ObjectMapper mapper = new ObjectMapper();
            String arr = ConnectData.rs.getString(1);
            int[] pp1 = mapper.readValue(arr, int[].class);
            friendlist.setUsername(pp1);
        }
        ConnectData.MySQLDisconnect ();
        return friendlist;
    }
}

