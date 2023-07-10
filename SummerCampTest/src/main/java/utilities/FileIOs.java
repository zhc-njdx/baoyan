package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileIOs {
    
    /**
     * 使用BufferedReader将文件中的内容读取出来
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
        return Collections.emptyList();
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
        return Collections.emptyList();
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
        return Collections.emptyList();
    }
    
    /**
     * 将指定内容写进文件中
     * 接收多个字符串列表，一个作为一行写入文件
     * @param filename 文件名
     * @param content 文件内容
     */
    public static void writeFile(String filename, boolean append, String... content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            for (String s : content) {
                writer.write(s+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断两个文件内容是否一致
     * @param file1 文件1
     * @param file2 文件2
     * @return 是否一致
     */
    public static boolean diff(String file1, String file2) {
        try(BufferedReader reader1 = new BufferedReader(new FileReader(file1));
            BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {
            long lines1 = reader1.lines().count();
            long lines2 = reader2.lines().count();
            if (lines1 != lines2) return false;

            String line1;
            String line2;
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                if (!line1.equals(line2)) return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除目录下的匹配某个格式(包含pattern字符串)的全部文件
     * @param dir 目录
     * @param pattern 格式
     * @return 是否完成删除
     */
    public static boolean deleteFiles(String dir, String pattern) {
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) return false;
            for (File file : files) {
                if (file.isFile() && file.getName().contains(pattern)) {
                    boolean delete = file.delete();
                    if (!delete) {
                        System.out.println("Fail to delete " + file.getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 创建一个新的文件
     * @param filename 文件名
     * @return 是否创建成功
     */
    public static boolean createFile(String filename) {
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                System.out.println("Fail to create the new file");
                return false;
            }
        } catch (IOException e) {
            System.out.println("IOException when create the file " + filename);
            return false;
        }
    }
    

}
