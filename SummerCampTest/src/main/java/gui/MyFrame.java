package gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MyFrame extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    
    public static final int CONTENT = 400;
    
    public static final int COMPONENT_HEIGHT = 25;
    public static final int BUTTON_WIDTH = 60;
    public static final int SINGLE_WORD_WIDTH = 10;
    
    
    public static final int MARGIN = (WIDTH-CONTENT)/2;
    
    public MyFrame(String title) {
        setTitle(title);
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    
}
