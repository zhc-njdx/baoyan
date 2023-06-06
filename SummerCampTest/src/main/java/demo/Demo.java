package demo;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Demo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("全文搜索系统");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
    
        
        JLabel label = new JLabel("请选择要搜索的文本文件");
        label.setFont(new Font("楷体", Font.BOLD, 20));
        label.setSize(250, 30);
    
        JButton button = new JButton("浏览");
        button.setSize(100, 50);
        button.addActionListener(e -> System.out.println("选择文件"));
        
        frame.getContentPane().add(BorderLayout.EAST, label);
        frame.getContentPane().add(BorderLayout.EAST, button);
        
        frame.setVisible(true);
    }
}
