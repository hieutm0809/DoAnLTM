/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import doan.Connection.MessageFriendDAO;
import doan.Connection.MessageFriendDTO;
/**
 *
 * @author ADMIN
 */
public class MessageFriendBUS {
    public MessageFriendDTO showMessageFriend(int participant1,int participant2) throws JsonProcessingException{
        MessageFriendDTO messagefriend = new MessageFriendDTO();
        MessageFriendDAO messagefriendDAO = new MessageFriendDAO();
        messagefriend = messagefriendDAO.getMessageByParticipantsID(participant1, participant2);
        return messagefriend;
    }
}
