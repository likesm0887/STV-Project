package adapter.device;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

import java.util.List;

public interface DeviceDriver {
    void startService();
    void stopService();
    void launchApp();
    void stopApp();
    void restartApp();
    void restartAppAndCleanData();
    void pauseApp();
    void reopenApp();
    void pressBackKey();
    void pressEnter();
    void selectTomorrow();
    void selectSomeday();
    void rotate();
    void waitFor(int millis);
    String getActivityName();

    MobileElement waitForElement(String xPath);
    MobileElement waitForElement(String xPath, int timeOutInSeconds);
    List<MobileElement> waitForElements(String xPath);
    void waitAndClickElement(String xPath);
    void waitAndTypeText(String xPath, String text);
    void waitAndSwipeElement(String xPath, SwipeElementDirection direction, int offset);
    void waitAndScrollToElement(String xPath, SwipeElementDirection direction);
    void waitAndDragElement(String xPath, int xOffset, int yOffset);
    void deleteCharacter(String xPath, int times);
    void pressPercentage(String xPath, int percent);

    void assertExist(String xPath);
    void assertNotExist(String xPath);
    void assertText(String xPath, String expectedText);
    void assertView(String expectActivity);
    void assertElementCount(String xPath, int expectedCount);
    void assertTextExist(String text);
}
