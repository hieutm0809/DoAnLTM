package Dang.LTM_Tuan10;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Dang.LTM_Tuan10.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUIDangNhap extends JFrame {
    JButton btnLogin;
    JTextField txtID;
    JPanel panel;
    
    public GUIDangNhap(){
        this.setSize(350,200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        txtID = new JTextField();
        txtID.setBounds(10, 50, 200, 60);
        this.add(txtID);
        
        btnLogin = new JButton("Login");
        btnLogin.setBounds(230, 60, 80, 40);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.name = txtID.getText();
                String[] arguments = new String[] {"123"};
                try {
                    client.main(arguments);
                } catch (IOException ex) {
                    Logger.getLogger(GUIDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUIDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
                hideGUI();
            }
        });
        this.add(btnLogin);
       
        this.setVisible(true);
        
    }
    
    public void hideGUI(){
        this.setVisible(false);
    }
    
    public static void main(String[] args) {
        GUIDangNhap test = new GUIDangNhap();
    }
}
