package adapter.coverage;

import entity.Config;

import java.io.File;
import java.io.IOException;

public class CodeCovergerator {
private final String PACKAGE_NAME="org.dmfs.tasks";
private int  coverageCounter =0;
    public void PullCodeCoverage(Config config) throws InterruptedException, IOException {
        final String COVERAGE_PATH = System.getProperty("user.dir") + "/"  + "/CodeCoverage";
        new File(COVERAGE_PATH).mkdirs();
        File coverageFile = new File(COVERAGE_PATH + "/coverage" + this.coverageCounter + ".ec");
        String[] pullToLocalCmd = {"adb","exec-out", "run-as", PACKAGE_NAME, "cat", "/data/data/" + PACKAGE_NAME + "/files/coverage.ec"};
        ProcessBuilder processBuilder = new ProcessBuilder(pullToLocalCmd);
        processBuilder.redirectOutput(coverageFile);
        Process process = processBuilder.start();
        process.waitFor();
        this.coverageCounter += 1;
    }
}
