package sjtu.exam.q2019;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class HashingFrame extends JFrame {
    
    CuckooHashTable ht;
    JPanel[] entries = new JPanel[16];
    Deque<Node> path;
    
    public HashingFrame() {
        ht = new CuckooHashTable();
        
        setTitle("Hashing");
        setLayout(null);
        
        int screenWidth = 1000;
        int screenHeight = 800;
        
        int labelWidth = 20;
        int labelHeight = 20;
    
        int textFieldWidth = 200;
        int textFieldHeight = 20;
    
        int buttonWidth = 60;
        int buttonHeight = 25;
        
        int gap = 5;
        int y = 50;
    
        int margin = (screenWidth - (2*labelWidth+2*textFieldWidth+2*buttonWidth+5*gap)) / 2;
        
        JLabel key = new JLabel("键");
        key.setBounds(margin, y, labelWidth, labelHeight);
        
        JTextField keyField = new JTextField();
        keyField.setBounds(key.getX()+labelWidth+gap, y, textFieldWidth, textFieldHeight);
        
        JLabel value = new JLabel("值");
        value.setBounds(keyField.getX()+textFieldWidth+gap, y, labelWidth, labelHeight);
        
        JTextField valueField = new JTextField();
        valueField.setBounds(value.getX()+labelWidth+gap, y, textFieldWidth, textFieldHeight);
    
        JButton insert = new JButton("插入");
        insert.setBounds(valueField.getX()+textFieldWidth+gap, y, buttonWidth, buttonHeight);
        insert.addActionListener(e -> doInsert(keyField.getText(), valueField.getText()));
        
        JButton remove = new JButton("删除");
        remove.setBounds(insert.getX()+buttonWidth+gap, y, buttonWidth, buttonHeight);
        remove.addActionListener(e -> doRemove(keyField.getText()));
        
        add(key);
        add(keyField);
        add(value);
        add(valueField);
        add(insert);
        add(remove);
        
        drawHashFunction();
        drawHashTable();
        
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void drawHashFunction() {
        JLabel h1 = new JLabel("H1(x) = x mod 8");
        h1.setBounds(150, 150, 150, 20);
        add(h1);
    
        JLabel h2 = new JLabel("H2(x) = (x div 8) mod 8");
        h2.setBounds(500, 150, 150, 20);
        add(h2);
    }
    
    private void drawHashTable() {
        for (int i = 0; i < 16; i ++) {
            entries[i] = new JPanel();
            entries[i].setBackground(Color.WHITE);
            entries[i].setBorder(new LineBorder(Color.BLACK));
            if (i < 8) entries[i].setBounds(200, 200+i*50, 50, 50);
            else entries[i].setBounds(500, 200+(i%8)*50, 50, 50);
            
            String key = ht.getKeyByIndex((i/8)+1, i%8);
            if (!key.equals("null")) {
                System.out.println("key " + key);
            }
            JLabel keyLabel = new JLabel(key);
            keyLabel.setBounds(entries[i].getX()+25, entries[i].getY()+25, 10, 10);
            entries[i].add(keyLabel);
            
            JLabel indexText = new JLabel((i%8)+"");
            if (i < 8) indexText.setBounds(entries[i].getX()-25, entries[i].getY()+25, 25, 25);
            else indexText.setBounds(entries[i].getX()+55, entries[i].getY()+25, 25, 25);
            
            add(entries[i]);
            add(indexText);
        }
    }
    
    private void clear() {
        for (int i = 0; i < 16; i ++) {
            remove(entries[i]);
        }
    }
    
    private void drawMovePath() {
        System.out.println("draw move path...");
        Graphics g = getGraphics();
        while (!path.isEmpty()) {
            Node node = path.pop();
            int ft = node.getFt();
            int fi = node.getFi();
            int tt = node.getTt();
            int ti = node.getTi();
            System.out.println(node.getKv().getKey() + " from table#" + ft + " index#" + fi + "to table#" + tt + " index#" + ti);
            int x1 = ft==1 ? 250 : 500;
            int y1 = 200+fi*50+25;
            int x2 = tt==1 ? 250 : 500;
            int y2 = 200+ti*50+25;
            g.drawLine(x1+5, y1+25, x2+5, y2+25);
        }
    }
    
    private void doInsert(String key, String value) {
        System.out.println("insert " + key);
        ht.set(key, value);
        path = ht.getPath();
        if (path != null) {
            drawMovePath();
        }
        clear();
        drawHashTable();
    }
    
    private void doRemove(String key) {
        System.out.println("remove " + key);
        ht.delete(key);
        clear();
        drawHashTable();
    }
}
