/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

/**
 *
 * @author ADMIN
 */
public class FriendListDTO {
    private int userID;
    private String username;
    
    public FriendListDTO(int userID,String username){
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
