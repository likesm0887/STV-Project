package adapter.device;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.ScreenOrientation;

import java.util.List;

public interface DeviceDriver {
    void startService();
    void stopService();
    void launchApp();
    void stopApp();
    void restartApp();
    void restartAppAndCleanData();

    MobileElement waitForElement(String xPath);
    MobileElement waitForElement(String xPath, int timeOutInSeconds);
    List<MobileElement> waitForElements(String xPath);
    void waitAndClickElement(String xPath);
    void waitAndTypeText(String xPath, String text);
    void waitAndSwipeElement(String xPath, SwipeElementDirection direction, int offset);

    void pressBackKey();
    void pressDeleteKey(int times);
    void rotate(ScreenOrientation screenOrientation);
    void waitFor(int millis);
}
