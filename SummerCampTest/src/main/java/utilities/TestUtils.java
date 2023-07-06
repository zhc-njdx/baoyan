package utilities;

import cppexam.memorymanagement.Main;

public class TestUtils {
    public static final String TEST_DIR = "E:\\Edownload\\memory-management\\test_cases\\";
    public static final String TEST_FILE_FORMAT = "test_%d.in";
    public static final String RESULT_FILE_FORMAT = "test_%d.out";
    public static final String OUTPUT_FILE_FORMAT = "output_%d.out";
    public static final int TESTCASE_NUMBER = 20;
    
    public void test() {
        for (int i = 1; i <= TESTCASE_NUMBER; i ++) {
            String testFile = String.format(TEST_DIR+TEST_FILE_FORMAT, i);
            String outputFIle = String.format(TEST_DIR+OUTPUT_FILE_FORMAT, i);
            Main.main(new String[]{testFile, outputFIle});
            String result = String.format(TEST_DIR+RESULT_FILE_FORMAT, i);
            if (!FileIOs.diff(outputFIle, result)) {
                System.out.println(outputFIle + " != " + result);
            }
        }
    }
    
}
