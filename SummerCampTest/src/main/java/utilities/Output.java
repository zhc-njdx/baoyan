package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * 控制输出流的位置
 */
public class Output {
    public static final int STANDARD_OUTPUT = 0;
    public static final int ERROR_OUTPUT = 1;
    public static final int FILE_OUTPUT = 2;

    public static void output(String content, int to, String... options) {
        switch (to) {
            case STANDARD_OUTPUT:
                standardOutput(content);
                break;
            case ERROR_OUTPUT:
                errorOutput(content);
                break;
            case FILE_OUTPUT:
                fileOutput(content, options);
                break;
            default:
                break;
        }
    }

    private static void standardOutput(String content) {
        System.out.print(content);
    }

    private static void errorOutput(String content) {
        System.err.print(content);
    }

    private static void fileOutput(String content, String... options) {
        String filename = options[0];
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
