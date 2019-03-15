package Adapter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumAdapter implements DeviceDriver {
    private AppiumDriverLocalService service;
    private  AndroidDriver driver;
    private final String PACKAGE_NAME="org.dmfs.tasks";
    public AppiumAdapter() throws IOException, InterruptedException {
        setUpAppium();
        driver=createAppium();
    }
    @Override
    public void find() {

    }

    @Override
    public void click() {

    }
    private  void setUpAppium() throws IOException, InterruptedException
    {
        this.service = new AppiumServiceBuilder().usingPort(5700).build();
        this.service.start();
    }
    private AndroidDriver createAppium() throws MalformedURLException
    {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME,"Android Emulator");
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9");
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"org.dmfs.tasks");
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"org.dmfs.tasks.TaskListActivity");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        cap.setCapability("newCommandTimeout",10000);
        return new AndroidDriver(new URL("http://0.0.0.0:5700/wd/hub"), cap);
    }
    @Override
    public void launchApp() throws IOException, InterruptedException {
        String[] initInstrActivityCmd = {"adb", "-s", "emulator-5554", "shell", "am", "instrument", "-w","-r", "-e","debug","false","-e","class","''org.dmfs.tasks.utils.tasks.TaskListActivityTest#testInitPrint''", PACKAGE_NAME + ".test/android.support.test.runner.AndroidJUnitRunner"};
        ProcessBuilder proc = new ProcessBuilder(initInstrActivityCmd);
        proc.start();
        Thread.sleep(4000);

    }
}
