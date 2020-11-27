/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class UserDAO {
    public ArrayList docDSSP(){
        MySQLConnect ConnectData = new MySQLConnect();
        ArrayList dsuser = new ArrayList<UserDTO>();
        try{
            String qry = "select * from user";
            ConnectData.st = ConnectData.conn.createStatement();
            ConnectData.rs = ConnectData.st.executeQuery(qry);
            while(ConnectData.rs.next()){
                UserDTO user = new UserDTO();
                user.setId(ConnectData.rs.getString(1));
                user.setPass(ConnectData.rs.getString(2));
                dsuser.add(user);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
        ConnectData.MySQLDisconnect();
        return dsuser;
    }
}
