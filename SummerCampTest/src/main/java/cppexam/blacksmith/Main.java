package cppexam.blacksmith;

import utilities.Logging;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String TEST_FILE = "/Users/zhenghanchao/zhc/baoyan/SummerCampTest/src/main/java/cppexam/blacksmith/test2.txt";
    public static final Logging log = new Logging("log.txt");
    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File(TEST_FILE))) {
            String s = in.nextLine();
            int num = Integer.parseInt(s.split(" ")[0]);
            int limit = Integer.parseInt(s.split(" ")[1]);

            List<Blacksmith> blacksmiths = new ArrayList<>();
            for (int i = 0; i < num; i ++) {
                s = in.nextLine();
                int uid = Integer.parseInt(s.split(" ")[0]);
                int type = Integer.parseInt(s.split(" ")[1]);
                blacksmiths.add(new Blacksmith(uid, type));
            }
            BlacksmithShop shop = new BlacksmithShop(limit, blacksmiths);

            List<Command> commands = new ArrayList<>();
            int n = Integer.parseInt(in.nextLine());
            for (int i = 0; i < n; i ++) {
                String cmd = in.nextLine();
                commands.add(new Command(cmd, shop));
            }

            for (Command command : commands) {
                shop.schedule(); // 调度一次
                command.execute(); // 执行命令
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
