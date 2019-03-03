import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Controller {
    private  AndroidDriver driver;




    public void execute() throws IOException, InterruptedException {
        
        setUpAppium();
        driver=createAppium();
        driver.launchApp();
        MobileElement elementTwo = (MobileElement) driver.findElementByClassName("android.widget.ImageView");
        elementTwo.click();

        service.stop();

    }
    private AppiumDriverLocalService service;
    private  void setUpAppium() throws IOException , InterruptedException
    {
        this.service = new AppiumServiceBuilder().usingPort(5000).build();
        this.service.start();
    }
    private AndroidDriver createAppium() throws MalformedURLException
    {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME,"Android device");
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"org.dmfs.tasks");
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"org.dmfs.tasks.TaskListActivity");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        cap.setCapability("newCommandTimeout",10000);
        return new AndroidDriver(new URL("http://0.0.0.0:5000/wd/hub"), cap);
    }
}