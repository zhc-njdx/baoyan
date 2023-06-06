package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOs {
    
    /**
     * 将文件中的内容读取出来
     * @param filename 文件名
     * @return 文件内容
     */
    public static List<String> readFileByBuffer(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> result = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 使用Scanner读取文件内容
     * @param filename 文件名
     * @return 文件内容
     */
    public static List<String> readFileByScanner(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            List<String> result = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 读取csv文件内容
     * @param filename csv文件名
     * @return csv文件内容 每行是一个String[]
     */
    public static List<String[]> readCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String[]> result = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                result.add(s);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将指定内容写进文件中
     * @param filename 文件名
     * @param content 文件内容
     */
    public static void writeFile(String filename, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String s : content) {
                writer.write(s+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

}