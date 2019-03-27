package adapter;

import config.Config;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static java.lang.Thread.sleep;

public class AppiumAdapter implements DeviceDriver {
    private String ADB_PATH ="C:\\Users\\hong\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";
    public AndroidDriver getDriver() {
        return driver;
    }

    private AndroidDriver driver;
    private Config config;
    private AppiumDriverLocalService appiumDriverLocalService;

    public AppiumAdapter(Config config) throws IOException, InterruptedException {
        this.config = config;
        createAppiumService();
        startAppiumService();
        driver = createAndroidDriver();
    }

    public void launchApp() throws IOException, InterruptedException {
        driver.closeApp();
       // String[] initInstrActivityCmd = {"adb", "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w","-r", "-e","debug","false","-e","class","''org.dmfs.tasks.utils.tasks.TaskListActivityTest#testInitPrint''", "org.dmfs.tasks.utils.tasks" + ".test/android.support.test.runner.AndroidJUnitRunner"};
        Thread.sleep(1000);
        String[] initInstrActivityCmd = {ADB_PATH, "shell", "am", "instrument", "-w", "-e", "coverage", "true", "org.dmfs.tasks.test/android.support.test.runner.AndroidJUnitRunner"};
        ProcessBuilder proc = new ProcessBuilder(initInstrActivityCmd);
        proc.start();
        Thread.sleep(1000);
    }

    public AndroidDriver createAndroidDriver() {
        String[] initInstrActivityCmd = {ADB_PATH, "-s", config.getSerialNumber(), "shell", "am", "instrument", "-w","-r", "-e","debug","false","-e","class","''org.dmfs.tasks.utils.tasks.TaskListActivityTest#testInitPrint''", "org.dmfs.tasks.utils.tasks" + ".test/android.support.test.runner.AndroidJUnitRunner"};

        DesiredCapabilities cap = createDesiredCapabilities();

        URL serverUrl = createServerUrl();
        try {
            executeCmd(initInstrActivityCmd);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new AndroidDriver(serverUrl, cap);
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

    private void startAppiumService() throws IOException, InterruptedException {
        appiumDriverLocalService.start();
    }

    public void executeCmd(String... cmd) throws IOException, InterruptedException {
        ProcessBuilder proc = new ProcessBuilder(cmd);
        Process p = proc.start();
    }


    private  void setUpAppium() throws IOException, InterruptedException
    {
        appiumDriverLocalService.start();
    }

    public void closeAppiumService() {
        appiumDriverLocalService.stop();
    }

    public MobileElement findElement(String xPath, MobileElement... parent) {
        MobileElement element = parent.length > 0 ?
                parent[0].findElement(By.xpath(xPath)) :
                (MobileElement) driver.findElement(By.xpath(xPath));
        return element;
    }

    public List<MobileElement> findElements(String xPath, MobileElement... parent) {
        List<MobileElement> elements = parent.length > 0 ?
                parent[0].findElements(By.xpath(xPath)) :
                driver.findElements(By.xpath(xPath));
        return elements;
    }

    public MobileElement clickElement(String xPath, MobileElement... parent) {
        MobileElement element = findElement(xPath, parent);
        element.click();
        return element;
    }

    public MobileElement sendKeysAtElement(String xPath, String keys, MobileElement... parent) {
        MobileElement element = findElement(xPath, parent);
        element.sendKeys(keys);
        return element;
    }

    public void clickBack() {
        driver.navigate().back();
    }

    public void waitFor(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {

        }
    }

    public adapter.ScreenSize getScreenSize() {
        Dimension size = driver.manage().window().getSize();
        return new adapter.ScreenSize(size.width, size.height);
    }

    public void scrollVertical(int x, int fromY, int toY) {
        scroll(x, fromY, x, toY);
    }

    public void scrollHorizontal(int y, int fromX, int toX) {
        scroll(fromX, y, toX, y);
    }

    private void scroll(int startX, int startY, int endX, int endY) {
        TouchAction action = new TouchAction(driver);
        action.longPress(startX, startY).moveTo(endX, endY).release().perform();
    }

    @Override
    public MobileElement findElement(String xPath) {
        return (MobileElement)this.driver.findElement(By.xpath(xPath));
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
    public void swipeElement(String xPath, SwipeElementDirection swipeDirection) {
        this.findElement(xPath).swipe(swipeDirection, 100, 100, 100);
    }

    @Override
    public void rotation(ScreenOrientation screenOrientation) {

    }

    @Override
    public void restartApp() {

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
