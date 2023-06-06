package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenteredTextApp {
    
    public static void main(String[] args) {
        // 创建 Swing 程序的主线程
        SwingUtilities.invokeLater(() -> {
            // 创建 JFrame 对象
            JFrame frame = new JFrame("居中文本");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // 创建面板
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // 获取面板的宽度和高度
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    
                    // 设置标签的字体和大小
                    Font font = new Font("Arial", Font.PLAIN, 18);
                    g.setFont(font);
                    
                    // 获取标签的宽度和高度
                    FontMetrics fm = g.getFontMetrics();
                    int textWidth = fm.stringWidth("这是一个居中的文本");
                    int textHeight = fm.getHeight();
                    
                    // 计算标签的起始坐标，使其垂直居中显示在面板中间
                    int x = (panelWidth - textWidth) / 2;
                    int y = (panelHeight - textHeight) / 2 + fm.getAscent();
                    
                    // 绘制标签
                    g.drawString("这是一个居中的文本", x, y);
                }
            };
            // 设置面板的布局为 BorderLayout
            panel.setLayout(new BorderLayout());
            // 设置面板的背景色
            panel.setBackground(Color.WHITE);
            // 添加面板到 JFrame 中
            frame.add(panel);
            
            // 创建按钮
            JButton button = new JButton("按钮");
            // 设置按钮的最小高度为 0，这样按钮的高度就可以根据内容自动调整
            button.setMinimumSize(new Dimension(0, 0));
            // 添加按钮点击事件监听器
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "按钮被点击了！");
                }
            });
            // 添加按钮到面板中的 EAST 位置，即右侧
            panel.add(button, BorderLayout.EAST);
            
            // 设置 JFrame 的大小、位置和可见性
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null); // 居中显示
            frame.setVisible(true);
        });
    }
}


