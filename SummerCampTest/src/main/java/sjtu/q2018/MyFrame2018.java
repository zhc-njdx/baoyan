package sjtu.q2018;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFrame2018 extends JFrame {
    
    public MyFrame2018() {
        setTitle("数据真实性检测工具");
        setLayout(null);
        
        int begin = 325;
    
        JLabel text = new JLabel("数据文件");
        text.setBounds(begin, 10, 100, 40);
        text.setFont(new Font("楷体", Font.PLAIN, 24));
    
        JTextField fileName = new JTextField();
        fileName.setBounds(begin+110, 10, 200, 40);
        fileName.setFont(new Font("Arial", Font.PLAIN, 24));
    
        JButton selectButton = new JButton("选择");
        selectButton.setBounds(begin+330, 10, 100, 40);
        selectButton.setFont(new Font("楷体", Font.PLAIN, 24));
        selectButton.addActionListener(e -> fileName.setText(fileSelect()));
        
        JButton checkButton = new JButton("检测");
        checkButton.setBounds(begin+450, 10, 100, 40);
        checkButton.setFont(new Font("楷体", Font.PLAIN, 24));
        checkButton.addActionListener(e -> doCheck(fileName.getText()));
        
        add(text);
        add(fileName);
        add(selectButton);
        add(checkButton);
        
        setSize(1200, 800);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    private String fileSelect() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(".csv文件", "csv"));
        int code = fileChooser.showOpenDialog(getContentPane());
        if (code == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            while (code == JFileChooser.APPROVE_OPTION && !file.getName().endsWith("csv")) {
                JOptionPane.showMessageDialog(fileChooser, "请选择csv文件", "文件类型错误",
                    JOptionPane.ERROR_MESSAGE);
                code = fileChooser.showOpenDialog(getContentPane());
                file = fileChooser.getSelectedFile();
            }
            return file.getAbsolutePath();
        }
        return "";
    }
    
    private void doCheck(String filename) {
        FileContent fileContent = new FileContent(filename);
        List<List<Data>> subSequence = fileContent.divide();
        List<Check> checks = new ArrayList<>();
        checks.add(new Check(subSequence.get(0), false));
        checks.add(new Check(subSequence.get(1), false));
        checks.add(new Check(subSequence.get(2), false));
        checks.add(new Check(subSequence.get(3), false));
        checks.add(new Check(subSequence.get(0), true));
        checks.add(new Check(subSequence.get(1), true));
        checks.add(new Check(subSequence.get(2), true));
        checks.add(new Check(subSequence.get(3), true));
        checks.forEach(Check::check);
        draw(checks);
    }
    
    private void draw(List<Check> result) {
        int graph = 150;
        int leftMargin = 50;
        int padding = 100;
        int topMargin = 50;
        int directCopyCnt = 0;
        int reversalCopyCnt = 0;
        for (int k = 0; k < result.size(); k ++) {
            Check check = result.get(k);
            int len = check.result.length;
            int l = 200/len;
            for (int i = 0; i < len; i ++) {
                for (int j = 0; j < len; j ++) {
                    if (check.result[i][j] <= 0) continue;
                    // 统计顺序和逆序情况下直接复制的数量
                    if (check.result[i][j] > 5) {
                        if (k < 4) directCopyCnt ++;
                        else reversalCopyCnt ++;
                    }
                    JPanel panel = new JPanel();
                    int x = padding+leftMargin+(k%4)*(graph+leftMargin)+i*l;
                    int y;
                    if (k < 4) y = padding+topMargin+graph-j*l;
                    else y = padding+topMargin+(graph+topMargin+50)+graph-j*l;
                    panel.setBounds(x, y, l, l);
                    panel.setBackground(new Color(100*check.result[i][j]/100+150, 0, 0));
                    this.add(panel);
                }
            }
            // 加上背景板
            JPanel panel0 = new JPanel();
            panel0.setBackground(Color.WHITE);
            int bX = padding+leftMargin+(graph+leftMargin)*(k%4);
            int bY = 0;
            if (k < 4) bY += padding+topMargin;
            else bY += padding+topMargin+graph+topMargin+50;
            panel0.setBounds(bX, bY, graph+5, graph+5);
            this.add(panel0);
            // 加上x y轴
            int zeroX = bX;
            int zeroY = bY+graph;
            int beginX = 150*(k%4);
            int beginY = beginX+75;
            // 画x轴
            for (int i = 0; i < 7; i ++) {
                JLabel label;
                if (i != 6) label = new JLabel(beginX+"");
                else label = new JLabel("x");
                label.setBounds(zeroX+25*i, zeroY, 25, 25);
                this.add(label);
                beginX+=15;
            }
            // 画y轴
            for (int i = 0; i < 7; i ++) {
                JLabel label;
                if (i != 6) label = new JLabel(beginY+"");
                else label = new JLabel("y");
                label.setBounds(zeroX-25, zeroY-25*(i+1), 25, 25);
                this.add(label);
                beginY+=15;
            }
            // 画复制数目
            if (k % 4 == 3) {
                JLabel label = new JLabel();
                if (k < 4) label.setText("直接复制"+directCopyCnt+"处");
                else label.setText("逆序复制"+reversalCopyCnt+"处");
                label.setBounds(padding+leftMargin, zeroY+50, 100, 25);
                this.add(label);
            }
        }
    }
}
