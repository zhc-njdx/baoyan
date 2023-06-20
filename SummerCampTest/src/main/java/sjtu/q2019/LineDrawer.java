package sjtu.q2019;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class LineDrawer extends JPanel {
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(10, 10, 100, 100); // 绘制一条从(10,10)到(100,100)的直线
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Line Drawer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        Graphics g = frame.getGraphics();
        g.drawLine(10, 10, 100, 100);
    }
}
