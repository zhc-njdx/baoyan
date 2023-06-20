package gui;

import utilities.Utility;

import javax.swing.*;

/**
 * 带横向和纵向滚轮的文本区域 用于2017年
 */
public class ScrollableTextAreaExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Scrollable TextArea Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JTextArea textArea = new JTextArea();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i ++) {
            sb.append("This is a long text that may exceed the visible area of the JTextArea. "
                    + "By adding a JScrollPane, the text can be scrolled horizontally or vertically.\n");
        }
        textArea.setText(sb.toString());

        JButton button = new JButton("set");
        button.setBounds(0, 0, 50, 40);
        button.addActionListener(e -> textArea.setText(Utility.getRandomStr(100)));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(button);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

