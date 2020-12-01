/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import static doan.Connection.UserBUS.dsuser;
import doan.Connection.FriendListDAO;
import doan.Connection.FriendListDTO;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class FriendListBUS {
    
    public FriendListDTO findFriendListByID(int userID) throws SQLException, JsonProcessingException {
        FriendListDTO friendList = new FriendListDTO();
        FriendListDAO friendListDAO = new FriendListDAO();
        friendList = friendListDAO.findFriendListByID(userID);
        return friendList;
    } 
}
