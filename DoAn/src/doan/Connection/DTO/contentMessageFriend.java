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
public class contentMessageFriend {
    private int from;
    private String time;
    private String content;
    public contentMessageFriend(){
        
    }
    public contentMessageFriend(int from, String time, String content){
        this.from = from;
        this.time = time;
        this.content = content;
    }

    public int getFrom() {
        return from;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
