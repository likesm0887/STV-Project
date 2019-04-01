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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class AppiumDriver implements DeviceDriver {
    private final int DEFAULT_TIMEOUT = 10;
    private AndroidDriver driver;
    private Config config;
    private AppiumDriverLocalService appiumDriverLocalService;
    private final String ADB_PATH = System.getenv("ANDROID_HOME") + "\\platform-tools\\adb.exe";

    public AppiumDriver(Config config) {
        this.config = config;
        createAppiumService();
        startAppiumService();
        driver = createAndroidDriver();
    }

    public void stopApp() throws IOException {
        String[] stopCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "force-stop", "org.dmfs.tasks"};
        this.executeCmd(stopCmd);

    }

    private void clearAppData() throws IOException {
        String[] command = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "pm", "clear", "org.dmfs.tasks"};
        this.executeCmd(command);
    }

    public void restart(List<String> isCleanApp) throws IOException, InterruptedException {

        stopApp();

        if (!isCleanApp.isEmpty()) {
            this.clearAppData();
        }
        launchApp();

    }

    public void launchApp() throws IOException, InterruptedException {
        Thread.sleep(1000);
        String[] initInstrActivityCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w", "-e", "coverage", "true", "org.dmfs.tasks.test/android.support.test.runner.AndroidJUnitRunner"};
        ProcessBuilder proc = new ProcessBuilder(initInstrActivityCmd);
        proc.start();
        Thread.sleep(1000);
    }

    public AndroidDriver createAndroidDriver() {
        String[] initInstrActivityCmd = {"adb", "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w", "-r", "-e", "debug", "false", "-e", "class", "''org.dmfs.tasks.utils.tasks.TaskListActivityTest#testInitPrint''", "org.dmfs.tasks.utils.tasks" + ".test/android.support.test.runner.AndroidJUnitRunner"};

        DesiredCapabilities cap = createDesiredCapabilities();

        URL serverUrl = createServerUrl();
        try {
            executeCmd(initInstrActivityCmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new AndroidDriver(serverUrl, cap);
    }

    public AndroidDriver getDriver() {
        return driver;
    }

    private URL createServerUrl() {
        URL serverUrl = null;
        try {
            serverUrl = new URL("http://0.0.0.0:" + config.getAppiumPort() + "/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return serverUrl;
    }

    private DesiredCapabilities createDesiredCapabilities() {
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

    private void createAppiumService() {
        appiumDriverLocalService = new AppiumServiceBuilder()
                .usingPort(config.getAppiumPort())
                .build();
    }

    private void startAppiumService() {
        appiumDriverLocalService.start();
    }

    public void executeCmd(String... cmd) throws IOException {
        ProcessBuilder proc = new ProcessBuilder(cmd);
        Process p = proc.start();
    }


    private void setUpAppium() {
        appiumDriverLocalService.start();
    }

    public void closeAppiumService() {
        appiumDriverLocalService.stop();
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
        final int DEFAULT_DURATION = 500;
        findElement(xPath).swipe(direction, offset, offset, DEFAULT_DURATION);
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

    @Override
    public void restartApp(String... isCleanApp) {
        List<String> inputList = Arrays.asList(isCleanApp);

        try {
            this.restart(inputList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void launchApplication() {
        this.driver.launchApp();
    }


    @Override
    public void waitUntilElementShow(String xPath) {

        WebDriverWait wait = new WebDriverWait(this.driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath(xPath)));
    }
}
