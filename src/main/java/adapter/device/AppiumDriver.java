package adapter.device;

import adapter.coverage.CodeCoverGenerator;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import entity.Config;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class AppiumDriver implements DeviceDriver {
    private final int DEFAULT_SWIPE_DURATION = 300;
    private final String ADB_PATH = Paths.get(System.getenv("ANDROID_HOME"), "platform-tools", "adb").toString();
    private int defaultTimeout;
    private AppiumDriverLocalService appiumDriverLocalService;
    private AndroidDriver driver;
    private AppiumAsserter appiumAsserter;
    private Config config;
    private CodeCoverGenerator codeCovergenerator = new CodeCoverGenerator(config);

    public AppiumDriver(Config config) {
        this.config = config;
        defaultTimeout = 3;
        appiumDriverLocalService = getAppiumService();
        appiumAsserter = new AppiumAsserter(this, this.config);
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
        try {
            return new URL("http://0.0.0.0:" + config.getAppiumPort() + "/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("The error format of appium server url");
        }
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
        waitFor(1000);
    }

    @Override
    public void reopenApp() {
        String[] switchApp = new String[]{ADB_PATH, "-s", config.getSerialNumber(), "shell", "input", "keyevent", "KEYCODE_APP_SWITCH"};
        executeCmd(switchApp);
        waitFor(1500);
        executeCmd(switchApp);
        waitFor(1000);
    }

    public MobileElement findElement(String xPath) {
        return (MobileElement) this.driver.findElement(By.xpath(xPath));
    }

    public List<MobileElement> findElements(String xPath) {
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
        waitForElement(xPath).click();
    }

    @Override
    public void waitAndTypeText(String xPath, String text) {
        waitForElement(xPath).sendKeys(text);
    }

    @Override
    public void waitAndSwipeElement(String xPath, SwipeElementDirection direction, int offset) {
        MobileElement element = waitForElement(xPath);
        element.swipe(direction, offset, offset, DEFAULT_SWIPE_DURATION);
    }

    @Override
    public void waitAndScrollToElement(String xPath, SwipeElementDirection direction) {
        final int FIND_ELEMENT_LIMIT_TIMES = 25;

        int findElementTimes = 0;
        int offset = 70;
        MobileElement scrollView = findScrollRootElement();
        SwipeElementDirection gestureDirection = getOppositeDirection(direction);

        List<MobileElement> result = scrollView.findElementsByXPath(xPath);
        while (result.size() == 0 && findElementTimes < FIND_ELEMENT_LIMIT_TIMES) {
            scrollView.swipe(gestureDirection, offset, offset, 1500);
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
    public void pressEnter() {
        waitFor(500);
        driver.pressKeyCode(66);
    }

    @Override
    public void pressBackKey() {
        waitFor(1000);
        driver.navigate().back();
    }

    @Override
    public void selectTomorrow() {
        waitFor(500);
        LocalDate localDate =  LocalDate.now();
        LocalDate tomorrow = localDate.plusDays(1);
        if(!localDate.getMonth().equals(tomorrow.getMonth())){
            waitAndClickElement("//*[@resource-id='android:id/next']");
        }
        waitAndClickElement("//*[@class='android.view.View' and @text='" + tomorrow.getDayOfMonth() + "']");
    }

    @Override
    public void selectSomeday() {
        waitFor(500);
        LocalDate localDate =  LocalDate.now();
        LocalDate someday = localDate.plusDays(2);
        if(!localDate.getMonth().equals(someday.getMonth())){
            waitAndClickElement("//*[@resource-id='android:id/next']");
        }
        waitAndClickElement("//*[@class='android.view.View' and @text='" + someday.getDayOfMonth() + "']");
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
        appiumAsserter.assertExist(xPath, "Element does not exist");
    }

    @Override
    public void assertText(String xPath, String expectedText) {
        appiumAsserter.assertText(xPath, expectedText);
    }

    @Override
    public void assertElementCount(String xPath, int expectedCount) {
        appiumAsserter.assertElementCount(xPath, expectedCount);
    }

    @Override
    public void assertTextExist(String text) {
        appiumAsserter.assertTextExist(text);
    }

    @Override
    public void assertView(String expectView) {
        appiumAsserter.assertView(expectView);

    }

    @Override
    public String getActivityName() {
        return appiumAsserter.getActivityName();
    }

}
