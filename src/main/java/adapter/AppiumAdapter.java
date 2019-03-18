package adapter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AppiumAdapter {
    private AndroidDriver driver;
    private final int SERVER_PORT = 5700;
    public AppiumAdapter() {
        driver = getDriver();
    }

    public AndroidDriver getDriver() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.dmfs.tasks");
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.dmfs.tasks.TaskListActivity");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        cap.setCapability("newCommandTimeout", 10000);
        URL serverUrl = null;
        try {
            serverUrl = new URL("http://0.0.0.0:" + SERVER_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AndroidDriver driver = new AndroidDriver(serverUrl, cap);
        return driver;
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
            Thread.sleep(millis);
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
}
