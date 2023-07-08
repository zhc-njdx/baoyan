package cppexam.weibo;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String test = "/Users/zhenghanchao/zhc/baoyan/SummerCampTest/src/main/java/cppexam/weibo/test.txt";
        try (Scanner scanner = new Scanner(new File(test))) {
            int n = Integer.parseInt(scanner.nextLine());
            Platform platform = Platform.getPlatform();
            for (int i = 0; i < n; i ++) {
                String cmd = scanner.nextLine();
                String[] split = cmd.split(" ");
                switch (split[0]) {
                    case "post":
                        platform.doPost(split[1], split[2], split[3]);
                        break;
                    case "follow":
                        platform.doFollow(split[1], split[2]);
                        break;
                    case "unfollow":
                        platform.doUnfollow(split[1], split[2]);
                        break;
                    case "getFollows":
                        platform.getUserFollowee(split[1]);
                        break;
                    case "getFans":
                        platform.getUserFans(split[1]);
                        break;
                    case "getWeibos":
                        platform.getUserWeibo(split[1]);
                        break;
                    case "getRecent":
                        platform.getUserRecentWeibo(split[1], Integer.parseInt(split[2]), split[3]);
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
