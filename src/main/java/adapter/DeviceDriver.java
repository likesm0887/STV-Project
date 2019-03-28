package adapter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.ScreenOrientation;

import java.util.List;

public interface DeviceDriver {
    // our defined operation
    MobileElement findElement(String xPath);
    List<MobileElement> findElements(String xPath);
    void clickElement(String xPath);
    void typeText(String xPath, String value);
    void swipeElement(String xPath, SwipeElementDirection direction, int offset);
    MobileElement waitForElement(String xPath);
    MobileElement waitForElement(String xPath, int timeOutInSeconds);
    List<MobileElement> waitForElements(String xPath);
    void waitAndClickElement(String xPath);
    void waitAndTypeText(String xPath, String text);

    void pressBackKey();
    void rotate(ScreenOrientation screenOrientation);
    void waitFor(int millis);
    void restartApp();
    void launchApplication();
    void waitUntilElementShow(String xPath);
}
