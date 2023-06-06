package sjtu.exam.q2017;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFrame extends JFrame {
    private FileContent fileContent;
    private JTextArea resultContent;
    
    public MyFrame() {
        setTitle("全文搜索系统");
        setLayout(null);
    
        JLabel text = new JLabel("请选择要搜索的文本文件");
        text.setBounds(390, 50, 160, 25);
        add(text);
    
        JButton button = new JButton("浏览");
        button.setBounds(550, 50, 60, 25);
        button.addActionListener(e -> openFile());
        add(button);
        
        setSize(1000, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
        int code = chooser.showOpenDialog(getContentPane());
        if (code == JFileChooser.APPROVE_OPTION) {
            List<String> content = new ArrayList<>();
            File file = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.add(line);
                }
                this.fileContent = new FileContent(content);
                // 生成统计词频的文件: output.txt
                CalWordFreq c = new CalWordFreq(this.fileContent);
                c.calc();
                // 打开搜素窗口
                openSearchWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void openSearchWindow() {
        JFrame searchWindow = new JFrame();
        searchWindow.setLayout(null);
        
        
        JLabel text = new JLabel("请输入搜索项");
        text.setBounds(0, 0, 200, 40);
        text.setFont(new Font("楷体", Font.PLAIN, 24));
        
        JTextField searchOption = new JTextField("I && am");
        searchOption.setBounds(200, 0, 700, 40);
        searchOption.setFont(new Font("Arial", Font.PLAIN, 24));
        
        JButton search = new JButton("确定");
        search.setBounds(900, 0, 100, 40);
        search.setFont(new Font("楷体", Font.PLAIN, 24));
        search.addActionListener(e -> doSearch(searchOption.getText()));
        
        JLabel result = new JLabel("搜索结果");
        result.setBounds(0, 50, 1000, 40);
        result.setFont(new Font("楷体", Font.PLAIN, 24));
        
        resultContent = new JTextArea();
        resultContent.setFont(new Font("Arial", Font.PLAIN, 16));
        resultContent.setLineWrap(true);
        resultContent.setEditable(false);
        resultContent.setBounds(0, 100, 1000, 500);
        resultContent.setFont(new Font("Arial", Font.PLAIN, 24));
    
//        JScrollPane scrollPane = new JScrollPane(resultContent);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        searchWindow.add(text);
        searchWindow.add(searchOption);
        searchWindow.add(search);
        searchWindow.add(result);
        searchWindow.add(resultContent);
//        searchWindow.getContentPane().add(scrollPane);
        
        searchWindow.setLocationRelativeTo(this);
        searchWindow.setSize(1000, 600);
        searchWindow.setVisible(true);
    }
    
    private void doSearch(String options) {
        // 搜索的逻辑
        OptionParser optionParser = new OptionParser(options);
        Rule rule = optionParser.parse();
        Search search = new Search(rule, fileContent);
        search.doSearch();
        resultContent.setText(search.toString());
//        resultContent.scrollRectToVisible(resultContent.getVisibleRect());
    }
}
