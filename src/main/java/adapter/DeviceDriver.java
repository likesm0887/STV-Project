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
    void swipeElement(String xPath, SwipeElementDirection swipeDirection);
    void rotation(ScreenOrientation screenOrientation);
    void restartApp();
    void launchApplication();
    void waitUntilElementShow(String xPath);

//
//
//
//    MobileElement find(String xpath);
//
//
//    MobileElement findByClassName(String xpath);
//
//    MobileElement findByName(String name);
//
//    void click(String name) throws InterruptedException;
//    void launchApp() throws IOException, InterruptedException;
}
