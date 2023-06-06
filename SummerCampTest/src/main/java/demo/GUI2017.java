package demo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI2017 extends JFrame {
    
    private List<String> lines;
    
    public static void main(String[] args) {
        GUI2017 frame = new GUI2017();
        frame.setVisible(true);
    }
    
    public GUI2017() {
        setTitle("全文搜索系统");
        setBounds(100, 100, 600, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 120));
        
        JLabel label = new JLabel("请选择要搜索的文本文件");
        label.setFont(new Font("楷体", Font.PLAIN, 20));
        label.setSize(300, 30);
    
        JButton button = new JButton("浏览");
        button.setBackground(new Color(0xff, 0xff, 0xff));
        button.setFont(new Font("楷体", Font.PLAIN, 15));
        button.setSize(100, 40);
        
        button.addActionListener(e -> selectFile());
        
        add(label);
        add(button);
        
        setLocationRelativeTo(null);
    }
    
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
        int code = fileChooser.showOpenDialog(getContentPane());
        if (code == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                lines = new ArrayList<>();
                String line;
                int count = 0;
                while ((line=reader.readLine()) != null) {
                    lines.add(line);
                    count++;
                }
                System.out.println(count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
