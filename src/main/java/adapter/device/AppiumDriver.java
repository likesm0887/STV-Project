package adapter.device;

import adapter.coverage.CodeCoverGenerator;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import entity.Config;
import entity.exception.AssertException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AppiumDriver implements DeviceDriver {
    private final int DEFAULT_SWIPE_DURATION = 300;
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private AndroidDriver driver;
    private Config config;
    private int defaultTimeout;
    private int findElementLimitTimes = 10;
    private AppiumDriverLocalService appiumDriverLocalService;
    private CodeCoverGenerator codeCovergenerator = new CodeCoverGenerator(config);
    private BiMap<String, String> viewAndActivityMap = HashBiMap.create();

    public AppiumDriver(Config config) {
        this.config = config;
        defaultTimeout = 3;
        appiumDriverLocalService = getAppiumService();
        CreateViewAndActivityMatchTable();
    }

    private AppiumDriverLocalService getAppiumService() {
        return new AppiumServiceBuilder().usingPort(config.getAppiumPort())
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                .build();
    }

    @Override
    public void startService() {
        appiumDriverLocalService.start();
        driver = new AndroidDriver(getServerUrl(), getDesiredCapabilities());
    }

    @Override
    public void stopService() {
        driver.quit();
        appiumDriverLocalService.stop();
    }

    private void executeCmd(String... cmd) {
        try {
            ProcessBuilder proc = new ProcessBuilder(cmd);
            proc.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getServerUrl() {
        URL serverUrl = null;
        try {
            serverUrl = new URL("http://0.0.0.0:" + config.getAppiumPort() + "/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("The error format of appium server url");
        }
        return serverUrl;
    }

    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, config.getDevicesName());
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getAndroidVersion());
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.dmfs.tasks");
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.dmfs.tasks.TaskListActivity");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        cap.setCapability("autoLaunch", "false");
        return cap;
    }

    public AndroidDriver getDriver() {
        return driver;
    }

    public void setDefaultTimeout(int second) {
        this.defaultTimeout = second;
    }

    @Override
    public void launchApp() {
        String[] initInstrActivityCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w", "-e", "coverage", "true", "org.dmfs.tasks.test/android.support.test.runner.AndroidJUnitRunner"};
        executeCmd(initInstrActivityCmd);
        waitFor(1500);
    }

    @Override
    public void restartApp() {
        stopApp();
        launchApp();
    }

    @Override
    public void restartAppAndCleanData() {
        stopApp();
        clearAppData();
        launchApp();
    }

    @Override
    public void stopApp() {
        String[] stopTestBroadcastCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "broadcast", "-a", "\"test\""};
        this.executeCmd(stopTestBroadcastCmd);
        waitFor(2000);
        codeCovergenerator.pullCodeCoverage();
        String[] stopCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "force-stop", "org.dmfs.tasks"};
        this.executeCmd(stopCmd);
        waitFor(500);
    }

    private void clearAppData() {
        String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "pm", "clear", "org.dmfs.tasks"};
        this.executeCmd(command);
        waitFor(1000);
    }

    @Override
    public void pauseApp() {
        String[] pressHomeKey = new String[]{ADB_PATH, "-s", config.getSerialNumber(), "shell", "input", "keyevent", "KEYCODE_HOME"};
        executeCmd(pressHomeKey);
        waitFor(500);
    }

    @Override
    public void reopenApp() {
        String[] switchApp = new String[]{ADB_PATH, "-s", config.getSerialNumber(), "shell", "input", "keyevent", "KEYCODE_APP_SWITCH"};
        executeCmd(switchApp);
        waitFor(1000);
        executeCmd(switchApp);
        waitFor(1000);
    }

    private MobileElement findElement(String xPath) {
        return (MobileElement) this.driver.findElement(By.xpath(xPath));
    }

    private List<MobileElement> findElements(String xPath) {
        return this.driver.findElements(By.xpath(xPath));
    }

    @Override
    public MobileElement waitForElement(String xPath, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        MobileElement e = wait.until((ExpectedCondition<MobileElement>) webDriver -> webDriver.findElement(By.xpath(xPath)));
        return e;
    }

    @Override
    public MobileElement waitForElement(String xPath) {
        return waitForElement(xPath, defaultTimeout);
    }

    @Override
    public List<MobileElement> waitForElements(String xPath) {
        waitForElement(xPath);
        return findElements(xPath);
    }

    @Override
    public void waitAndClickElement(String xPath) {
        waitForElement(xPath, defaultTimeout).click();
    }

    @Override
    public void waitAndTypeText(String xPath, String text) {
        waitForElement(xPath, defaultTimeout).sendKeys(text);
    }

    @Override
    public void waitAndSwipeElement(String xPath, SwipeElementDirection direction, int offset) {
        MobileElement element = waitForElement(xPath);
        element.swipe(direction, offset, offset, DEFAULT_SWIPE_DURATION);
    }

    @Override
    public void waitAndScrollToElement(String xPath, SwipeElementDirection direction) {
        int findElementTimes = 0;
        int offset = 50;
        MobileElement scrollView = findScrollRootElement();
        SwipeElementDirection gestureDirection = getOppositeDirection(direction);

        List<MobileElement> result = scrollView.findElementsByXPath(xPath);
        while (result.size() == 0 && findElementTimes < this.findElementLimitTimes) {
            scrollView.swipe(gestureDirection, offset, offset, 600);
            result = scrollView.findElementsByXPath(xPath);
            findElementTimes++;
        }

        if (result.size() == 0)
            throw new ElementNotFoundException(xPath, "", "");
    }

    private SwipeElementDirection getOppositeDirection(SwipeElementDirection direction) {
        switch (direction) {
            case UP:
                return SwipeElementDirection.DOWN;
            case DOWN:
                return SwipeElementDirection.UP;
            case RIGHT:
                return SwipeElementDirection.LEFT;
            case LEFT:
                return SwipeElementDirection.RIGHT;
            default:
                throw new RuntimeException("Can't find the opposite direction...");
        }
    }

    private MobileElement findScrollRootElement() {
        return waitForElement("//*[@class='android.widget.ScrollView' or @class='android.widget.ListView']");
    }

    @Override
    public void waitAndDragElement(String xPath, int xOffset, int yOffset) {
        WebElement element = waitForElement(xPath);
        int centerX = element.getLocation().x + element.getSize().width / 2;
        int centerY = element.getLocation().y + element.getSize().height / 2;
        new TouchAction(driver)
                .longPress(centerX, centerY, 300)
                .waitAction(300)
                .moveTo(centerX + xOffset, centerY + yOffset)
                .release()
                .perform();
    }

    @Override
    public void deleteCharacter(String xPath, int times) {
        this.waitAndClickElement(xPath);
        for (int i = 0; i < times; i++)
            driver.pressKeyCode(AndroidKeyCode.DEL);
    }

    @Override
    public void pressPercentage(String xPath, int percent) {
        MobileElement element = waitForElement(xPath);
        int x = element.getLocation().getX() + element.getSize().width * percent / 100 - 10;
        int y = element.getLocation().getY() + element.getSize().height / 2;
        new TouchAction(driver).tap(x, y).perform();
    }

    @Override
    public void pressBackKey() {
        driver.navigate().back();
    }

    @Override
    public void rotate() {
        if (driver.getOrientation() == ScreenOrientation.PORTRAIT)
            driver.rotate(ScreenOrientation.LANDSCAPE);
        else
            driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Override
    public void waitFor(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void assertExist(String xPath) {
        try {
            waitForElement(xPath, 2);
        } catch (Exception e) {
            throw new AssertException("Element does not exist");
        }
    }

    @Override
    public void assertText(String xPath, String expectedText) {
        assertExist(xPath);
        MobileElement element = findElement(xPath);
        if (!element.getText().equals(expectedText)) {
            throw new AssertException(
                    "\nActual text: " + element.getText() + "\n" +
                            "Expect: " + expectedText + "\n");
        }
    }

    @Override
    public void assertElementCount(String xPath, int expectedCount) {
        assertExist(xPath);
        int actual = findElements(xPath).size();
        if (actual != expectedCount) {
            throw new AssertException(
                    "\nActual count: " + actual + "\n" +
                            "Expected: " + expectedCount + "\n");
        }
    }

    @Override
    public void assertView(String expectView) {
        String actualView = convertActivityToView(getActivityName());
        if (!expectView.equals(actualView)) {
            throw new AssertException(
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

    public String getActivityName() {
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

    private static List<String> convertInputStreamToStringList(InputStream is) {
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

    public static String parseActivityName(InputStream is) {
        List<String> result = convertInputStreamToStringList(is);
        String firstLing = result.get(0);
        String activityName = firstLing.substring(firstLing.indexOf("/") + 1, firstLing.indexOf("}"));
        String[] str = activityName.split(" ");
        return str[0].replace(".", "");
    }
}
