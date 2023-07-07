package utilities;

import cppexam.memorymanagement.Main;

public class TestUtils {
    public static final String TEST_DIR = "/Users/zhenghanchao/Downloads/test_cases/";
    public static final String TEST_FILE_FORMAT = "test_%d.in";
    public static final String RESULT_FILE_FORMAT = "test_%d.out";
    public static final String OUTPUT_FILE_FORMAT = "output_%d.out";
    public static final int TESTCASE_NUMBER = 20;
    
    public static void test() {
        createOutputAgain();
        boolean hasError = false;
        for (int i = 1; i <= TESTCASE_NUMBER; i++) {
            String testFile = String.format(TEST_DIR + TEST_FILE_FORMAT, i);
            String outputFile = String.format(TEST_DIR + OUTPUT_FILE_FORMAT, i);
            Main.main(new String[]{testFile, outputFile});
            String result = String.format(TEST_DIR + RESULT_FILE_FORMAT, i);
            if (!FileIOs.diff(outputFile, result)) {
                hasError = true;
                System.out.println(outputFile + " != " + result);
            }
        }
        if (!hasError) {
            System.out.println("All Test Pass");
        }
    }

    public static void createOutputAgain() {
        // 删除现存的所有输出文件
        if (!FileIOs.deleteFiles(TEST_DIR, "output")) {
            return;
        }
        // 创建新的output文件
        for (int i = 1; i <= TESTCASE_NUMBER; i ++) {
            String outputFile = String.format(TEST_DIR + OUTPUT_FILE_FORMAT, i);
            Output.output("", Output.FILE_OUTPUT, outputFile);
        }
    }

    public static void main(String[] args) {
        TestUtils.test();
    }
    
}
