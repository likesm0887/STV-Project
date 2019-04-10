package adapter.device;

import entity.Config;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import static java.lang.Thread.sleep;

public class AppiumDriver implements DeviceDriver {
    private final int DEFAULT_TIMEOUT = 10;
    private final int DEFAULT_SWIPE_DURATION = 500;
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private AndroidDriver driver;
    private Config config;
    private AppiumDriverLocalService appiumDriverLocalService;

    public AppiumDriver(Config config) {
        this.config = config;
        appiumDriverLocalService = getAppiumService();
    }

    private AppiumDriverLocalService getAppiumService() {
        return new AppiumServiceBuilder()
                .usingPort(config.getAppiumPort())
                .build();
    }

    @Override
    public void startAppiumService() {
        appiumDriverLocalService.start();
        driver = new AndroidDriver(getServerUrl(), getDesiredCapabilities());
    }

    @Override
    public void stopAppiumService() {
        appiumDriverLocalService.stop();
    }

    public void executeCmd(String... cmd) throws IOException {
        ProcessBuilder proc = new ProcessBuilder(cmd);
        proc.start();
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

    @Override
    public void launchApp() {
        try {
            String[] initInstrActivityCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w", "-e", "coverage", "true", "org.dmfs.tasks.test/android.support.test.runner.AndroidJUnitRunner"};
            executeCmd(initInstrActivityCmd);
        } catch (IOException e) {
            throw new RuntimeException(ADB_PATH + " not found");
        }
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

    public void stopApp() {
        try {
            String[] stopCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "force-stop", "org.dmfs.tasks"};
            this.executeCmd(stopCmd);
            waitFor(1000);
        } catch (IOException e) {
            throw new RuntimeException(ADB_PATH + " not found");
        }
    }

    private void clearAppData() {
        try {
            String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "pm", "clear", "org.dmfs.tasks"};
            this.executeCmd(command);
            waitFor(1000);
        } catch (IOException e) {
            throw new RuntimeException(ADB_PATH + " not found");
        }
    }

    @Override
    public MobileElement findElement(String xPath) {
        return (MobileElement) this.driver.findElement(By.xpath(xPath));
    }

    @Override
    public List<MobileElement> findElements(String xPath) {
        return this.driver.findElements(By.xpath(xPath));
    }

    @Override
    public void clickElement(String xPath) {
        this.findElement(xPath).click();
    }

    @Override
    public void typeText(String xPath, String value) {
        this.findElement(xPath).sendKeys(value);
    }

    @Override
    public void swipeElement(String xPath, SwipeElementDirection direction, int offset) {
        findElement(xPath).swipe(direction, offset, offset, DEFAULT_SWIPE_DURATION);
    }

    @Override
    public MobileElement waitForElement(String xPath, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        MobileElement e = wait.until((ExpectedCondition<MobileElement>) webDriver -> webDriver.findElement(By.xpath(xPath)));
        return e;
    }

    @Override
    public MobileElement waitForElement(String xPath) {
        return waitForElement(xPath, DEFAULT_TIMEOUT);
    }

    @Override
    public List<MobileElement> waitForElements(String xPath) {
        waitForElement(xPath);
        return findElements(xPath);
    }

    @Override
    public void waitAndClickElement(String xPath) {
        MobileElement element = waitForElement(xPath, DEFAULT_TIMEOUT);
        element.click();
    }

    @Override
    public void waitAndTypeText(String xPath, String text) {
        MobileElement element = waitForElement(xPath, DEFAULT_TIMEOUT);
        element.sendKeys(text);
    }

    @Override
    public void waitAndSwipeElement(String xPath, SwipeElementDirection direction, int offset) {
        MobileElement element = waitForElement(xPath);
        element.swipe(direction, offset, offset, DEFAULT_SWIPE_DURATION);
    }

    @Override
    public void pressBackKey() {
        driver.navigate().back();
    }

    @Override
    public void rotate(ScreenOrientation screenOrientation) {
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
}
