package com.vcipher;

import javax.swing.SwingUtilities;

import com.vcipher.ui.Screen;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Screen().setVisible(true));
    }
}