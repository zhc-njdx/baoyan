package sjtu.q2019;

import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {
    public static void fc(String file1, String file2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
            BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {
            String line1;
            String line2;
            int cnt = 0;
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                if (!line1.equals(line2)) cnt++;
            }
            if ((line1 = reader1.readLine()) != null) {
                System.out.println(file1 + " is longer");
            }
            if ((line2 = reader2.readLine()) != null) {
                System.out.println(file2 + " is longer");
            }
            System.out.println("two files has " + cnt + " differences");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
