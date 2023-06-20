# GUI

## 1 创建Frame界面

### 1.1 统一不使用布局类型

使用布局类型复杂度较高，统一设置 `setLayout(null)`，然后通过 `setBounds(x, y, width, height)`
设置元素的排布位置。

### 1.2 常用元素

- `JButton`：按钮

- `JTextField`：文本域

- `JLabel`：标签

- `JFileChooser`：文件选择器
  - ```java
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
    int code = chooser.showOpenDialog(getContentPane());
    if (code == JFileChooser.APPROVE_OPTION) {
        // 相应处理逻辑
    }
  
- `JOptionPane.showMessageDialog`：消息提示框

  ```java
  	while(code != JFileChooser.APPROVE_OPTION || file.getName().endsWith("csv")) {
                  JOptionPane.showMessageDialog(chooser, "请选择csv文件", "文件类型错误", JOptionPane.ERROR_MESSAGE);
                  code = chooser.showOpenDialog(frame.getContentPane());
                  file = chooser.getSelectedFile();
              }
  ```

  
