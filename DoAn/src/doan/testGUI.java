package doan;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
public class testGUI extends JFrame {
    JButton btnUser1, btnUser2, btnSend;
    JLabel pnlName;
    JTextField txtContent;
    JTextArea txtAChat;
    
    public testGUI(){
        this.setSize(500,440);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        btnUser1 = new JButton("user2");
        btnUser1.setBounds(30, 60, 70, 30);
        this.add(btnUser1);
        
        btnUser2 = new JButton("user3");
        btnUser2.setBounds(30, 110, 70, 30);
        this.add(btnUser2);
        
        pnlName = new JLabel("....");
        pnlName.setBounds(120, 10, 80, 40);
        this.add(pnlName);
        
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
        this.add(btnSend);
        
        
        this.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        testGUI test = new testGUI();
    }
}
