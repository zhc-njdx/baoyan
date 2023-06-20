package sjtu.q2018;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileContent {
    List<Data> content;
    
    /**
     * 将csv文件中的数据读出
     * @param filename 数据文件名
     */
    public FileContent(String filename) {
        File file = new File(filename);
        content = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                content.add(new Data(data[0], data[1], data[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将数据按要求拆分成四个区间
     * @return 等距离的四个区间的数据序列
     */
    public List<List<Data>> divide() {
        List<List<Data>> result = new ArrayList<>();
        int len = content.size();
        result.add(content.subList(0, len/4));
        result.add(content.subList(len/4, len/2));
        result.add(content.subList(len/2, len*3/4));
        result.add(content.subList(len*3/4, len));
        return result;
    }
    
    
}
