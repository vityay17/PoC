package com.mycompany.pocnew.shared;

import javax.swing.JOptionPane;

public class GUIElements {
    public static void showMessage(String body){
        JOptionPane.showMessageDialog(null, body, "Message", JOptionPane.PLAIN_MESSAGE);
    }
}
