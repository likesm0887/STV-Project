package adapter.coverage;

import entity.Config;

import java.io.*;
import java.nio.file.Paths;

public class CodeCoverGenerator {
    private final String PACKAGE_NAME = "org.dmfs.tasks";
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private int coverageCounter = 0;
    private Config config;

    public CodeCoverGenerator(Config config) {
        this.config = config;
    }

    public void pullCodeCoverage() {
        final String COVERAGE_PATH = System.getProperty("user.dir") + "/" + "/codeCoverage";
        new File(COVERAGE_PATH).mkdirs();
        File coverageFile = new File(COVERAGE_PATH + "/coverage" + this.coverageCounter + ".ec");
        String[] pullToLocalCmd = {ADB_PATH, "exec-out", "run-as", PACKAGE_NAME, "cat", "/data/data/" + PACKAGE_NAME + "/files/coverage.ec"};
        ProcessBuilder processBuilder = new ProcessBuilder(pullToLocalCmd);
        processBuilder.redirectOutput(coverageFile);
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (checkFileCanUse(coverageFile)) {
            this.coverageCounter += 1;
        }
    }

    //
    private boolean checkFileCanUse(File coverageFile) {
        try {
            FileReader coverageFileReader = new FileReader(coverageFile);
            BufferedReader sequenceFileBufferReader = new BufferedReader(coverageFileReader);
            return !sequenceFileBufferReader.readLine().contains("No such file or directory");
        } catch (IOException e) {
            throw new RuntimeException("Code Coverage File is not found.");
        }
    }
}
