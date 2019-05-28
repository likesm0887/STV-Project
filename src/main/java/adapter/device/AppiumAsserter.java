package adapter.device;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import entity.Config;

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

    private BiMap<String, String> viewAndActivityMap = HashBiMap.create();

    public AppiumAsserter(AppiumDriver appiumDriver, Config config) {
        this.appiumDriver = appiumDriver;
        this.config = config;
        CreateViewAndActivityMatchTable();
    }

    public void assertText(String xPath, String expectedText) {
        assertExist(xPath);
        MobileElement element = this.appiumDriver.findElement(xPath);
        if (!element.getText().equals(expectedText)) {
            throw new entity.exception.AssertException(
                    "\nActual text: " + element.getText() + "\n" +
                            "Expect: " + expectedText + "\n");
        }
    }

    public void assertElementCount(String xPath, int expectedCount) {
        assertExist(xPath);
        int actual = this.appiumDriver.findElements(xPath).size();
        if (actual != expectedCount) {
            throw new entity.exception.AssertException(
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
            throw new entity.exception.AssertException(errorMessage);
        }
    }

    public String getActivityName() {
        String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "dumpsys",
                "activity", "activities", "|", "grep", "\"Run\\ #\""};
        InputStream inputStream = this.executeCmdExtractOutput(command);
        return parseActivityName(inputStream);
    }


    public void assertActivity(String expectActivity) {
        String actualActivity = getActivityName();
        if (!expectActivity.equals(actualActivity)) {
            throw new entity.exception.AssertException(
                    "\nActual activity: " + actualActivity + "\n" +
                            "Expect: " + expectActivity + "\n");
        }
    }


    public void assertView(String expectView) {
        String actualView = convertActivityToView(getActivityName());
        if (!expectView.equals(actualView)) {
            throw new entity.exception.AssertException(
                    "\nActual View: " + actualView + "(Activity:" + convertViewToActivity(actualView) + ")" +"\n" +
                            "Expect: " + expectView +  "\n");
        }
    }

    private void CreateViewAndActivityMatchTable() {
        viewAndActivityMap.put("TaskList", "TaskListActivity");
        viewAndActivityMap.put("EditTasks", "EditTaskActivity");
        viewAndActivityMap.put("ViewTask", "ViewTaskActivity");
        viewAndActivityMap.put("DisplayedLists", "SyncSettingsActivity");
    }


    private String convertViewToActivity(String view) {
        return viewAndActivityMap.get(view);
    }

    private String convertActivityToView(String activity) {
        return viewAndActivityMap.inverse().get(activity);
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

    private List<String> convertInputStreamToStringList(InputStream is) {
        List<String> result = new ArrayList<>();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = bReader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String parseActivityName(InputStream is) {
        List<String> result = convertInputStreamToStringList(is);
        String firstLing = result.get(0);
        String activityName = firstLing.substring(firstLing.indexOf("/") + 1, firstLing.indexOf("}"));
        String[] str = activityName.split(" ");
        return str[0].replace(".", "");
    }
}
