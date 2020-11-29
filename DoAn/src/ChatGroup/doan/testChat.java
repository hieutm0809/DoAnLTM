package doan;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import doan.Client;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class testChat extends JFrame {
    JButton btngr, btnSend;
    JLabel pnlChatTo, pnlName;
    JTextField txtContent;
    JTextArea txtAChat;
    
    public testChat(String name){
        this.setSize(500,440);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        
        pnlName = new JLabel(name);
        pnlName.setBounds(10, 10, 80,40);
        this.add(pnlName);
        
        pnlChatTo = new JLabel("gr1");
        pnlChatTo.setBounds(120, 10, 80, 40);
        this.add(pnlChatTo);
        
        txtAChat = new JTextArea(10,10);
        txtAChat.setBounds(120, 60, 300, 250);
        Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
        txtAChat.setBorder(border);
        this.add(txtAChat);
        
        txtContent = new JTextField();
        txtContent.setBounds(120, 330, 240, 60);
        this.add(txtContent);
        
        btnSend = new JButton("Send");
        btnSend.setBounds(370, 340, 70, 30);
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.executeSendMessage();
            }
        });
        this.add(btnSend);
        
        this.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        testChat test = new testChat("test");
    }
}
