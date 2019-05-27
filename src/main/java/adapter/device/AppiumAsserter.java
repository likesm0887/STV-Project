package adapter.device;

import entity.Config;
import entity.Exception.AssertException;
import io.appium.java_client.MobileElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppiumAsserter {
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private Config config;
    private AppiumDriver appiumDriver;


    public AppiumAsserter(AppiumDriver appiumDriver, Config config) {
        this.appiumDriver = appiumDriver;
        this.config = config;
    }

    public void assertText(String xPath, String expectedText) {
        assertExist(xPath);
        MobileElement element = this.appiumDriver.findElement(xPath);
        if (!element.getText().equals(expectedText)) {
            throw new AssertException(
                    "\nActual text: " + element.getText() + "\n" +
                            "Expect: " + expectedText + "\n");
        }
    }

    public void assertElementCount(String xPath, int expectedCount) {
        assertExist(xPath);
        int actual = this.appiumDriver.findElements(xPath).size();
        if (actual != expectedCount) {
            throw new AssertException(
                    "\nActual count: " + actual + "\n" +
                            "Expected: " + expectedCount + "\n");
        }
    }

    public void assertTextInCurrentActivity(String text) {
        String xPath = String.format("//*[@text='%s']", text);

        String errorMessage = String.format("\nActual : Text %s is not in the current activity" + "\n" +
                "Expected: Text %s is in the current activity" + "\n", text, text);

        assertExist(xPath, errorMessage);
    }

    public void assertExist(String xPath) {
        assertExist(xPath, "Element does not exist");
    }

    public void assertExist(String xPath, String errorMessage) {
        try {
            this.appiumDriver.waitForElement(xPath, 2);
        } catch (Exception e) {
            throw new AssertException(errorMessage);
        }
    }


    public void assertActivity(String expectActivity) {
        String actualActivity = getActivityName();
        if (!expectActivity.equals(actualActivity)) {
            throw new AssertException(
                    "\nActual activity: " + actualActivity + "\n" +
                            "Expect: " + expectActivity + "\n");
        }
    }

    private String getActivityName() {
        String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "dumpsys",
                "activity", "activities", "|", "grep", "\"Run\\ #\""};
        InputStream inputStream = this.executeCmdExtractOutput(command);
        return parseActivityName(inputStream);
    }

    private InputStream executeCmdExtractOutput(String... cmd) {
        ProcessBuilder proc = new ProcessBuilder(cmd);
        try {
            Process process = proc.start();
            process.waitFor();
            return process.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException("get Activity error");
        }
    }

    private static String parseActivityName(InputStream is) {
        List<String> result = new ArrayList<>();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bReader = new BufferedReader(reader);
        String line;
        try {
            line = bReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (line != null) {
            String activityName = line.substring(result.indexOf("/") + 1, result.indexOf("}"));
            String[] str = activityName.split(" ");
            return str[0].replace(".", "");
        }
        return "";
    }
}
