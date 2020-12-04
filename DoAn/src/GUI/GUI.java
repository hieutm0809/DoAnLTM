package GUI;

import com.google.gson.Gson;
import doan.Client;
import doan.Connection.DTO.infoGroup;
import doan.Connection.DTO.infoUser;
import doan.Connection.UserDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;

public class GUI extends JFrame {

    private JList<UserDTO> listUserDTO;
    JPanel pn_left, pn_right, pn_center, pn_top;
    JButton c_btn_send, c_btn_file, c_btn_sticker;
    JTextArea c_input, c_display;
    JLabel c_label, t_avatar, t_name, user1, user2, user3, r_name, r_email;
    Font font = new Font("Open Sans", Font.PLAIN, 20);
    Color outcolor = new Color(250, 250, 250);
    Color incolor = new Color(255, 255, 255);
    Color outline = new Color(230, 230, 230);
    Border nullBorder = BorderFactory.createEmptyBorder();
    Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, outline);
    Border leftBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, outline);
    Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, outline);
    Border rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 2, outline);
    public static String name;

    public GUI() {

        this.setTitle("Chat Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 650);
        this.centreWindow(this);
        this.setLayout(null);

    }

    public void displayGUI() {

        // Các thông tin về GUI này
        // Kích thước là 1000,650
        // Có 4 thành phần chia thành các panel riêng để hiện thị
        //******
        //***
        //Panel phía bên tay trái để hiện thị danh sách bạn bè 
//        pn_left = new JPanel();
//        pn_left.setLayout(null);
        pn_left = new JPanel();
        pn_left.setBackground(outcolor);
        pn_left.setBounds(0, 100, 250, 620);

        // Panel trung tâm để hiện thị khung chat, nhập liệu và nút gửi tin nhắn.
        pn_center = new JPanel();
        pn_center.setLayout(null);
        pn_center.setBounds(250, 100, 500, 620);

        c_label = new JLabel();
        c_label.setHorizontalAlignment(JLabel.CENTER);
        c_label.setFont(new Font("Open Sans", Font.BOLD, 16));
        c_label.setBounds(0, 0, 500, 35);
        c_label.setOpaque(true);
        c_label.setBackground(incolor);
        c_label.setBorder(bottomBorder);

        c_display = new JTextArea();
        c_display.setEditable(false);
        c_display.setBackground(incolor);
        c_display.setBounds(0, 35, 500, 400);

        c_input = new JTextArea();
        c_input.setBounds(0, 435, 400, 80);
        c_input.setBackground(incolor);
        c_input.setLineWrap(true);
        c_input.setWrapStyleWord(true);
        c_input.setBorder(topBorder);

        c_btn_send = new JButton("SEND");
        c_btn_send.setBounds(402, 435, 98, 40);
        c_btn_send.setBackground(outcolor);
        c_btn_send.setBorder(topBorder);
        c_btn_send.setFocusPainted(false);

        /*c_btn_file = new JButton();
        c_btn_file.setIcon(new ImageIcon(getClass().getResource("/Image/file.png")));
        c_btn_file.setBounds(402, 475, 49, 40);
        c_btn_file.setBackground(outcolor);
        c_btn_file.setBorder(topBorder);
        c_btn_file.setFocusPainted(false);*/

 /*c_btn_sticker = new JButton();
        c_btn_sticker.setIcon(new ImageIcon(getClass().getResource("/Image/sticker.png")));
        c_btn_sticker.setBounds(453, 475, 49, 40);
        c_btn_sticker.setBackground(outcolor);
        c_btn_sticker.setBorder(topBorder);
        c_btn_sticker.setFocusPainted(false);*/
        pn_center.add(c_label);
        pn_center.add(c_display);
        pn_center.add(c_input);
        pn_center.add(c_btn_send);
        //pn_center.add(c_btn_file);
        //pn_center.add(c_btn_sticker);
//        
        //Panel bên phải dùng dể hiện thị thông tin của người bạn đang chat/ thông tin nhóm

        pn_right = new JPanel();
        pn_right.setLayout(null);
        pn_right.setBackground(outcolor);
        pn_right.setBounds(750, 100, 250, 620);

        r_name = new JLabel();
        r_name.setFont(new Font("Open Sans", Font.BOLD, 20));
        r_name.setBounds(80, 20, 100, 70);

        pn_right.add(r_name);
        //Panel trên cùng dùng để hiện thị thông tin của user
        pn_top = new JPanel();
        pn_top.setLayout(null);
        pn_top.setBackground(outcolor);
        pn_top.setBounds(0, 0, 1000, 100);

        /*t_avatar = new JLabel();
        t_avatar.setIcon(new ImageIcon(getClass().getResource("/Image/avatar.png")));
        t_avatar.setBounds(25, 25, 50, 50);*/
        t_name = new JLabel();
        t_name.setText(name);
        t_name.setFont(new Font("Open Sans", Font.BOLD, 16));
        t_name.setBounds(85, 25, 150, 50);

        //pn_top.add(t_avatar);
        pn_top.add(t_name);

        //Add border vào các Panel
        pn_left.setBorder(rightBorder);
        pn_right.setBorder(leftBorder);
        pn_top.setBorder(bottomBorder);

        // Add cái Panel vào Frame
        this.add(pn_left);
        this.add(pn_top);
        this.add(pn_right);
        this.add(pn_center);
    }

    public void setC_Label(String temp) {
        c_label.setText(temp);
    }

    public void setT_Name(String temp) {
        t_name.setText(temp);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void FriendList(String arr) {
        infoUser[] respone = new Gson().fromJson(arr, infoUser[].class);
        DefaultListModel model = new DefaultListModel();

        for (infoUser s : respone) {
            System.out.println("Id: " + s.getId());
            System.out.println("Name: " + s.getFullname());
            System.out.println("Status: " + s.isOnline());
            model.addElement(s.getFullname());
        }
        JList list = new JList(model);
        list.setFont(new Font("Open Sans", Font.BOLD, 14));
        list.setBackground(new Color(250, 250, 250));
        list.setFixedCellHeight(50);
        list.setFixedCellWidth(230);

        Color outline = new Color(230, 230, 230);
        Border leftBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, outline);

        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                String s = (String) list.getSelectedValue();
                Client.gui.setC_Label(s);

            }
        });
        pn_left.add(list);
    }

    public void GroupChat(String arr) {
        infoGroup[] respone = new Gson().fromJson(arr, infoGroup[].class);
        DefaultListModel model = new DefaultListModel();

        for (infoGroup s : respone) {
            System.out.println("Id: " + s.getGroupID());
            System.out.println("Name: " + s.getGroupname());
            model.addElement(s.getGroupname());
        }
        JList list = new JList(model);
        list.setFont(new Font("Open Sans", Font.BOLD, 14));
        list.setBackground(new Color(250, 250, 250));
        list.setFixedCellHeight(50);
        list.setFixedCellWidth(230);

        Color outline = new Color(230, 230, 230);
        Border leftBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, outline);

        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

            }
        });
        pn_left.add(list);

    }
//    public static void main(String[] args) {
//        GUI test = new GUI();
//        test.displayGUI();
//        test.setVisible(true);
//    }
}
