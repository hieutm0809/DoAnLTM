package GUI;

import BUS.UserBUS;
import DTO.UserDTO;
import application.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class LoginView extends JFrame {

    public static JPanel panel;
    public static JLabel userLabel, passwordLabel;
    public static JTextField userText;
    public static JPasswordField passwordText;
    public static JButton loginButton, registerButton;
    Font font = new Font("Open Sans", Font.BOLD, 12);

    public LoginView() {

        this.setTitle("Chat Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(300, 150);
        this.setLayout(null);

    }

    public void displayGUI() {

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 300, 150);
        userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        userLabel.setFont(font);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setText("phong@gmail.com");
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        passwordLabel.setFont(font);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setText("123456");
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setBackground(new Color(244, 244, 244));
        loginButton.setFocusPainted(false);
        loginButton.setFont(font);
        loginButton.addActionListener((ActionEvent e) -> {
            String passtext = new String(passwordText.getPassword());
            if (userText.getText().equals("") || passtext.equals("")) {
                JOptionPane.showMessageDialog(null, "Không được để trống thông tin đăng nhập");
            } else {
                Client.executeSendMessage();
            }
        });
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(244, 244, 244));
        registerButton.setBounds(170, 80, 90, 25);
        registerButton.setFocusPainted(false);
        registerButton.setFont(font);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.status = "register";
                Client.guiRegister = new RegisterView();
                Client.guiRegister.displayGUI();
            }
        });
        panel.add(registerButton);
        this.add(panel);
    }

    public static void alert(String string) {
        JOptionPane.showMessageDialog(null, string);
    }
}
