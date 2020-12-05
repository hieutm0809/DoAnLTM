/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class MessageGroupDAO {
    public MessageGroupDTO getMessageByGroupID(int groupID) {
        MySQLConnect ConnectData = new MySQLConnect();
        MessageGroupDTO messagegroup = new MessageGroupDTO();
        try {
            String sql = "select * from messagegroup where groupID = '" + groupID + "'";
            ConnectData.st = ConnectData.conn.createStatement();
            ConnectData.rs = ConnectData.st.executeQuery(sql);
            while (ConnectData.rs.next()) {
                messagegroup.setGroupID(ConnectData.rs.getInt(1));
                messagegroup.setContent(ConnectData.rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("loi");
        }
        ConnectData.MySQLDisconnect();
        return messagegroup;
    }
}
