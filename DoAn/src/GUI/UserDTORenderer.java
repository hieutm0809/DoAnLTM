/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import doan.Connection.UserDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class UserDTORenderer extends JPanel implements ListCellRenderer<UserDTO> {

    private JLabel lbId = new JLabel();
    private JLabel lbPass = new JLabel();
    private String sendTo,modeChat;
 
    
    public UserDTORenderer() {
        setLayout(new BorderLayout(5, 5));
        JPanel panelText = new JPanel(new GridLayout(0, 1));
        panelText.add(lbId);
        add(panelText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends UserDTO> list,
            UserDTO user, int index, boolean isSelected, boolean cellHasFocus) {
        lbId.setText(user.getId());
        lbId.setOpaque(true);
        lbId.setFont(new Font("Open Sans", Font.PLAIN, 15));
        lbId.setPreferredSize(new Dimension(250, 50));
        // when select item
        if (isSelected) {
            lbId.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else { // when don't select
            lbId.setBackground(new Color(250, 250, 250));
            setBackground(list.getBackground());
        }
        return this;
    }
}
