package cppexam.memorymanagement;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static String TEST_FILE = "E:\\Edownload\\memory-management\\test_cases\\test_3.in";
    public static void main(String[] args) {
        String testFile = args[0];
        try (Scanner scanner = new Scanner(new File(testFile))) {
            String[] s = scanner.nextLine().split(" ");
            int bytes = Integer.parseInt(s[0]);
            int maxObjectNumber = Integer.parseInt(s[1]);
            Memory memory = new Memory(bytes);
            int commandNumber = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < commandNumber; i ++) {
                String[] cmd = scanner.nextLine().split(" ");
                switch (cmd[0]) {
                    case "ALLOC":
                        memory.alloc(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                        break;
                    case "FREE":
                        memory.free(Integer.parseInt(cmd[1]));
                        break;
                    case "SHOW":
                        memory.show();
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
