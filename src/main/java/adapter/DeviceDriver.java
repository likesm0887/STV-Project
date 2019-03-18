package adapter;

import io.appium.java_client.MobileElement;

import java.io.IOException;

public interface DeviceDriver {
    MobileElement find(String xpath);

    //WebElement find(String className, String name);

    MobileElement findByClassName(String xpath);

    MobileElement findByName(String name);

    void click(String name) throws InterruptedException;
    void launchApp() throws IOException, InterruptedException;
}
