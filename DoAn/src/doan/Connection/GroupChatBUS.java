/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import static doan.Connection.UserBUS.dsuser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class GroupChatBUS {

    public static ArrayList<GroupChatDTO> dsgroupchat;

    public void showGroupChat() throws SQLException, JsonProcessingException {
        GroupChatDAO data = new GroupChatDAO();
        if (dsgroupchat == null) {
            dsgroupchat = new ArrayList<GroupChatDTO>();
        }
        dsgroupchat = data.showGroupChat();
    }
}
