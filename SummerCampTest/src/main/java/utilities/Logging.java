package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

public class Logging {
    String logfile;
    public static final String FORMAT = "[%s]:\n%s\n";

    public Logging(String filename) {
        logfile = filename;
    }

    public void writeLog(String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true))) {
            writer.write(String.format(FORMAT, new Date(), log));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
