/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author ADMIN
 */
public class MessageGroupBUS {
    public MessageGroupDTO showMessageGroup(int groupID) throws JsonProcessingException{
        MessageGroupDTO messagegroup= new MessageGroupDTO();
        MessageGroupDAO messagegroupDAO = new MessageGroupDAO();
        messagegroup = messagegroupDAO.getMessageByGroupID(groupID);
        return messagegroup;
    }
}
