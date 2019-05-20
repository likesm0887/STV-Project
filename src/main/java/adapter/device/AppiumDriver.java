package adapter.device;

import adapter.coverage.CodeCovergerator;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import entity.Config;
import entity.Exception.AssertException;
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
import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;

public class AppiumDriver implements DeviceDriver {
    private final int DEFAULT_SWIPE_DURATION = 300;
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private AndroidDriver driver;
    private Config config;
    private int defaultTimeout;
    private int findElementLimitTimes = 10;
    private AppiumDriverLocalService appiumDriverLocalService;
    private CodeCovergerator codeCovergerator = new CodeCovergerator(config);

    public AppiumDriver(Config config) {
        this.config = config;
        defaultTimeout = 3;
        appiumDriverLocalService = getAppiumService();
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
        cap.setCapability("newCommandTimeout", 10000);
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
        codeCovergerator.pullCodeCoverage();
        String[] stopCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "force-stop", "org.dmfs.tasks"};
        this.executeCmd(stopCmd);
        waitFor(500);
    }

    private void clearAppData() {
        String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "pm", "clear", "org.dmfs.tasks"};
        this.executeCmd(command);
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
        // TODO: swipe the task
//        int x = element.getLocation().x;
//        int y = element.getLocation().y;
//        int width = element.getSize().width;
//        int height = element.getSize().height;
//        new TouchAction(driver)
//                .longPress(x + width / 2, y + height / 2, 300)
//                .waitAction(300)
//                .moveTo(x + width + 1000, y + height / 2)
//                .release()
//                .perform();
    }



    @Override
    public void waitAndScrollToElement(String xPath, SwipeElementDirection direction) {
        int findElementTimes = 0;
        WebElement scrollView = findScrollRootElement();

        WebElement result = null;
        // findElements.size
        while (findElementTimes <= findElementLimitTimes && result == null) {
            try {
                result = waitForElement(xPath, 1);
            }catch (TimeoutException e) {
                scrollToDirection(scrollView, direction);
            }
            findElementTimes++;
        }

        if (result == null)
            throw new ElementNotFoundException(xPath, "", "");
    }

    private WebElement findScrollRootElement() {
        WebElement element = null;
        try {
            element = findElement("//*[@class='android.widget.ScrollView']");
        } catch (TimeoutException e) {
            element = findElement("//*[@class='android.widget.ListView']");
        }
        return element;
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

    private void scrollToDirection(WebElement scrollView, SwipeElementDirection direction) {
        int x = scrollView.getLocation().x;
        int y = scrollView.getLocation().y;
        int width = scrollView.getSize().width;
        int height = scrollView.getSize().height;

        switch (direction) {
            case UP:
                driver.swipe(x + width / 2, y + (int) (height * 0.2), x + width / 2, y + (int) (height * 0.8), DEFAULT_SWIPE_DURATION);
                break;

            case LEFT:
                driver.swipe(x + (int) (width * 0.8), y + height / 2, x + (int) (width * 0.2), y + height / 2, DEFAULT_SWIPE_DURATION);
                break;

            case RIGHT:
                driver.swipe(x + (int) (width * 0.2), y + height / 2, x + (int) (width * 0.8), y + height / 2, DEFAULT_SWIPE_DURATION);
                break;

            case DOWN:
                driver.swipe(x + width / 2, y + (int) (height * 0.8), x + width / 2, y + (int) (height * 0.2), DEFAULT_SWIPE_DURATION);
                break;
        }
    }


    @Override
    public void deleteCharacter(String xPath, int times) {
        this.waitAndClickElement(xPath);
        for (int i = 0; i < times; i++)
            driver.pressKeyCode(AndroidKeyCode.DEL);
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
        } catch (InterruptedException e) {

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
