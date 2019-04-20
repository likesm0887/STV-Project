package integrationTest.deviceDriver;

import adapter.device.AppiumDriver;
import adapter.ConfigReader;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppiumDriverTest {
    private static AppiumDriver appiumDriver;
    private static AndroidDriver androidDriver;

    @BeforeClass
    public static void setUpClass() {
        ConfigReader configReader = new ConfigReader();
        appiumDriver = new AppiumDriver(configReader.getConfig());
        appiumDriver.startService();
        androidDriver = appiumDriver.getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
        appiumDriver.stopService();
    }

    @Before
    public void setUp() {
        appiumDriver.restartApp();
    }

    @Test
    public void androidDriverShouldNotBeNull() {
        assertNotNull(androidDriver);
    }

    @Test
    public void launchApp() {
        // TaskList view's menu_btn
        androidDriver.findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat']"));
    }

    @Test
    public void waitForElement() {
        // TasksList view's float_add_btn
        androidDriver.findElement(By.xpath("//*[@class='android.widget.ImageButton']")).click();
        // EditTasks view's cancel_btn
        MobileElement element = appiumDriver.waitForElement("//*[@class='android.widget.ImageButton']");
        assertNotNull(element);
    }

    @Test
    public void waitForElements() {
        // Tabs
        List<MobileElement> elements = appiumDriver.waitForElements("//*[@class='android.support.v7.app.ActionBar$Tab']");
        assertEquals(6, elements.size());
    }

    @Test
    public void waitAndClickElement() {
        // TaskList view's 'My tasks' list
        appiumDriver.waitAndClickElement("//*[@class='android.widget.TextView' and @text='My tasks']");
        // 'My tasks' list's quick add button
        MobileElement quickAddButton = (MobileElement) androidDriver.findElement(By.xpath("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']"));
        assertNotNull(quickAddButton);
    }

    @Test
    public void waitAndTypeText() {
        // TaskList view's 'My tasks' list
        appiumDriver.waitAndClickElement("//*[@class='android.widget.TextView' and @text='My tasks']");
        // 'My tasks' list's quick add button
        appiumDriver.waitAndClickElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        // quick add text field
        appiumDriver.waitAndTypeText("//*[@resource-id='android:id/input']", "task");
        MobileElement textField = (MobileElement) androidDriver.findElement(By.xpath("//*[@text='task']"));
        assertNotNull(textField);
    }

    @Test
    public void waitAndSwipeElement() {
        waitAndTypeText();
        // quick add save button
        appiumDriver.waitAndClickElement("//*[@resource-id='android:id/button1']");
        appiumDriver.waitAndSwipeElement("//*[@class='android.widget.LinearLayout' and ./android.widget.TextView[@text='task']]", SwipeElementDirection.LEFT, 10);
        appiumDriver.waitFor(1000);
        MobileElement element = (MobileElement) androidDriver.findElement(By.xpath("//*[@text='Today']"));
        assertNotNull(element);
    }
}