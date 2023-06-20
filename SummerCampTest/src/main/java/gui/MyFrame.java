package gui;

import javax.swing.*;

public class MyFrame extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public MyFrame(String title) {
        setTitle(title);
        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
