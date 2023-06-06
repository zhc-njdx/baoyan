package sjtu.exam.ans2018;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @program: HuhuSearch
 * @description:
 * @author: Annntn
 * @create: 2018-06-24 20:40
 **/

public class FileSelector {
    static class fileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            //选择文件还是文件夹
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //是否可以选择多个文件
            jfc.setMultiSelectionEnabled(false);
            //展现新的窗口
            jfc.showDialog(new JLabel(), "ok");
            //设置文件的过滤器
//            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
//            jfc.setFileFilter(filter);
            //获取选中的文件
            //File[] arrfiles = jfc.getSelectedFiles();
            File file = jfc.getSelectedFile();
            if (!file.getAbsolutePath().endsWith("csv")) {
                textField.setText("请选择csv格式的文件");
                return;
            }
            if (file != null) {
                if (file.isDirectory()) {
                    System.out.println("文件夹:" + file.getAbsolutePath());
                } else if (file.isFile()) {
                    System.out.println("文件:" + file.getAbsolutePath());
                    textField.setText(file.getAbsolutePath());
                }
                System.out.println(file.getName());
                System.out.println(file);
            }
        }
    }

    static class checkListener implements ActionListener {
        ArrayList<String> results;

        public void actionPerformed(ActionEvent e) {
            String filePath = textField.getText();
            if (filePath == null || filePath.equals("") || filePath.equals("请输入文件名") || filePath.equals("文件不存在，请重新输入") || filePath.equals("请选择csv格式的文件")) {
                textField.setText("Please choose file first");
                return;
            }
            ArrayList<Integer> times = new ArrayList<>();

            File file = new File(filePath);
//            if (file.exists()){
//                textField.setText("文件不存在，请重新输入");
//                return;
//            }
            if (!file.getAbsolutePath().endsWith("csv")) {
                textField.setText("请选择csv格式的文件");
                return;
            }
            pattern = new Pattern(filePath);

            try {
                pattern.check();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            draw();
        }
    }

    public static void draw() {
        pattern.print();
        int y1 = 150 + 50;
        int xy = 200;
        int[][] gap = {{
                0, 75, 150
        }, {
                150, 225, 300
        }, {
                300, 375, 450
        }, {
                450, 525, 600
        }};
        int total1=0;
        int total2=0;
        for (int i = 0; i < pattern.result.size(); i++) {
            int[][] temp = pattern.result.get(i);
            for (int j = 0; j < temp.length; j++) {
                for (int k = 0; k < temp[0].length; k++) {
                    int kk = 200 / 75;
                    int x1 = 100 + i * 250;
                    if (temp[j][k] > 0) {
                        if (temp[j][k]>5){
                            total1++;
                        }
                        JPanel panel = new JPanel();
                        panel.setBackground(new Color(100 * temp[j][k] / 100 + 150, 0, 0));
                        panel.setBounds(x1 + (j * kk), y1 + 200 - k * kk - 50, kk, kk);
                        frame.add(panel);
                    }
                }
            }
        }
        int y2 = 420;
        for (int i = 0; i < pattern.result1.size(); i++) {
            int[][] temp = pattern.result1.get(i);
            for (int j = 0; j < temp.length; j++) {
                for (int k = 0; k < temp[0].length; k++) {
                    int kk = 200 / 75;
                    int x2 = 100 + i * 250;
                    if (temp[j][k] > 0) {
                        if (temp[j][k]>5){
                            total2++;
                        }
                        JPanel panel = new JPanel();
                        panel.setBackground(new Color(100 * temp[j][k] / 100 + 150, 0, 0));
                        panel.setBounds(x2 + (j * kk), y2 + k * kk , kk, kk);
                        frame.add(panel);
                    }
                }
            }
        }
        int xy1 = 150+5;
        int bb = 180 / 6;
        for (int j = 0; j < 4; j++) {
            JPanel sub11 = new JPanel();
            int x1 = 100 + j * 250;
            sub11.setBounds(x1, y1, xy1, xy1);
            sub11.setBackground(Color.WHITE);
            frame.add(sub11);

            for (int i = gap[j][0]; i <= gap[j][1]; i += 15) {
                JLabel label = new JLabel(String.valueOf(i));
                label.setFont(new Font("",0,10));
                label.setBounds(x1 + ((i - gap[j][0]) / 15) * bb, y1 + xy - 50, 30, 30);
                frame.add(label);
            }
            JLabel labelx = new JLabel("x");
            labelx.setFont(new Font("",0,10));
            labelx.setBounds(x1 + ((gap[j][1] - gap[j][0]) / 15+1) * bb, y1 + xy - 50, 30, 30);
            frame.add(labelx);
            for (int i = gap[j][1]; i <= gap[j][2]; i += 15) {
                JLabel label = new JLabel(String.valueOf(i));
                label.setFont(new Font("",0,10));
                label.setBounds(x1 - 30, y1 + xy - ((i - gap[j][1]) / 15) * bb - 20 - 50, 30, 30);
                frame.add(label);
            }
            JLabel labely = new JLabel("y");
            labely.setFont(new Font("",0,10));
            labely.setBounds(x1 - 30+10, y1 + xy - ((gap[j][2] - gap[j][1]) / 15+1) * bb - 20 - 50-10+15, 30, 30);
            frame.add(labely);
        }

        for (int j = 0; j < 4; j++) {
            int x2 = 100 + j * 250;
            JPanel sub11 = new JPanel();
            sub11.setBounds(x2, y2, xy1, xy1);
            sub11.setBackground(Color.WHITE);
            frame.add(sub11);
            for (int i = gap[j][0]; i <= gap[j][1]; i += 15) {
                JLabel label = new JLabel(String.valueOf(i));
                label.setFont(new Font("",0,10));
                label.setBounds(x2 + ((i - gap[j][0]) / 15) * bb, y2 + xy- 50, 30, 30);
                frame.add(label);
            }
            JLabel labelx = new JLabel("x");
            labelx.setFont(new Font("",0,10));
            labelx.setBounds(x2 + ((gap[j][1] - gap[j][0]) / 15+1) * bb, y2 + xy - 50-10, 30, 30);
            frame.add(labelx);
            for (int i = gap[j][1]; i <= gap[j][2]; i += 15) {
                JLabel label = new JLabel(String.valueOf(i));
                label.setFont(new Font("",0,10));
                label.setBounds(x2 - 30, y2 + xy - ((i - gap[j][1]) / 15) * bb - 20- 50, 30, 30);
                frame.add(label);
            }
            JLabel labely = new JLabel("y");
            labely.setFont(new Font("",0,10));
            labely.setBounds(x2 - 30+10, y2 + xy - ((gap[j][2] - gap[j][1]) / 15+1) * bb - 20 - 50+15, 30, 30);
            frame.add(labely);
        }


        JTextField textField = new JTextField(50);
        textField.setBounds(100,630,200,30);
        textField.setText("直接复制："+String.valueOf(total1)+"处");
        frame.add(textField);
        JTextField textField1 = new JTextField(50);
        textField1.setBounds(100,670,200,30);
        textField1.setText("逆序复制："+String.valueOf(total2)+"处");
        frame.add(textField1);
        JTextField textField2 = new JTextField(50);
        textField2.setBounds(100,710,200,30);
        textField2.setText("阈值："+5);
        frame.add(textField2);



        frame.setVisible(true);
    }

    static Pattern pattern;

    public static JTextField textField;
    public static JTextField textField1;
    public static JTextArea textArea;
    static JFrame frame = new JFrame();

    //Users/yuannnn/Desktop/STJU/data.csv
    public static void main(String[] args) {
        frame.setSize(1000, 800);
        frame.setContentPane(new JPanel());
        JButton fileButton = new JButton("选择");
        fileButton.addActionListener(new fileListener());
        JButton checkButton = new JButton("检测");
        checkButton.addActionListener(new checkListener());
        textField = new JTextField(40);
        textField.setEditable(true);
        textField.setText("请输入文件名");
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(fileButton);
        panel.add(checkButton);
        panel.setBounds(150, 0, 900, 50);
        frame.setLayout(null);
        frame.add(panel);

        frame.setVisible(true);

    }
}

class MyDrawPanel extends JPanel {
    public void paintComponent(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(20, 50, 100, 100);
    }
}
