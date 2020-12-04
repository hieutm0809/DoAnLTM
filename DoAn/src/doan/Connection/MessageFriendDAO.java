/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import doan.Connection.MessageFriendDTO;
/**
 *
 * @author ADMIN
 */
public class MessageFriendDAO {

    public MessageFriendDTO getMessageByParticipantsID(int participant1, int participant2) {
        MySQLConnect ConnectData = new MySQLConnect();
        MessageFriendDTO messagefriend = new MessageFriendDTO();
        try {
            String sql = "select * from message where (participant1 = '" + participant1 + "' and participant2 = '" + participant2 + "') or (participant1 = '" + participant2 + "' and participant2 = '" + participant1 + "')";
            ConnectData.st = ConnectData.conn.createStatement();
            ConnectData.rs = ConnectData.st.executeQuery(sql);
            while (ConnectData.rs.next()) {
                int tmp = ConnectData.rs.getInt(1);
                messagefriend.setParticipant1(ConnectData.rs.getInt(2));
                messagefriend.setParticipant2(ConnectData.rs.getInt(3));
                messagefriend.setContent(ConnectData.rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("loi");
        }
        ConnectData.MySQLDisconnect();
        return messagefriend;
    }
}
