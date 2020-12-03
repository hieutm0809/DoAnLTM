/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection.DTO;

/**
 *
 * @author ADMIN
 */
public class infoGroup {
    private int groupID;
    private String groupname;
    public infoGroup(){
        
    }
    public infoGroup(int groupID, String groupname){
        this.groupID = groupID;
        this.groupname = groupname;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    
}
