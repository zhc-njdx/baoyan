package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ComponentExamples {
    public static void FileChooserUsing() {
        MyFrame frame = new MyFrame("文件选择器");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
        int code = chooser.showOpenDialog(frame.getContentPane());
        if (code == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            // 使用对话框，当文件类型不符合要求的时候不断弹出对话框，直到选择符合要求的文件
            while(code != JFileChooser.APPROVE_OPTION || file.getName().endsWith("csv")) {
                JOptionPane.showMessageDialog(chooser, "请选择csv文件", "文件类型错误", JOptionPane.ERROR_MESSAGE);
                code = chooser.showOpenDialog(frame.getContentPane());
                file = chooser.getSelectedFile();
            }
        }
    }
}
