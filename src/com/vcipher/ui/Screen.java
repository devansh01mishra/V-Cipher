package com.vcipher.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Screen extends JFrame {
    Screen() {
        JLabel label = new JLabel();
        label.setText("Application");
        label.setForeground(Color.MAGENTA);
        label.setBackground(Color.BLACK);

        this.add(label);
        this.setSize(500, 500);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
