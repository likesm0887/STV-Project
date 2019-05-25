package integrationTest.deviceDriver;

import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import com.google.common.io.CharSource;
import entity.Exception.AssertException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.input.ReaderInputStream;
import org.junit.*;
import org.openqa.selenium.By;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
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
        appiumDriver.setDefaultTimeout(2);
        appiumDriver.startService();
        androidDriver = appiumDriver.getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
        appiumDriver.stopService();
    }

    @Before
    public void setUp() {
        appiumDriver.restartAppAndCleanData();
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

    @Test
    public void deleteKeyTest() {
        // TaskList view's 'My tasks' list
        appiumDriver.waitAndClickElement("//*[@class='android.widget.TextView' and @text='My tasks']");
        // 'My tasks' list's quick add button
        appiumDriver.waitAndClickElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        // quick add text field
        appiumDriver.waitAndTypeText("//*[@resource-id='android:id/input']", "task");
        appiumDriver.deleteCharacter("//*[@resource-id='android:id/input']", 2);

        MobileElement element = (MobileElement) androidDriver.findElement(By.xpath("//*[@resource-id='android:id/input']"));
        assertEquals("ta", element.getText());
    }

    @Test
    public void assertExistTest() {
        appiumDriver.assertExist("//*[@class='android.widget.TextView' and @text='My tasks']");
    }

    @Test (expected = AssertException.class)
    public void assertExistFailTest() {
        appiumDriver.assertExist("//*[@class='android.widget.TextView' and @text='My tasks123']");
    }

    @Test
    public void assertTextTest() {
        appiumDriver.assertText("//*[@class='android.widget.TextView' and @text='My tasks']", "My tasks");
    }

    @Test (expected = AssertException.class)
    public void assertTextFailTest() {
        appiumDriver.assertText("//*[@class='android.widget.TextView' and @text='My tasks']", "My tasks123");
    }

    @Test (expected = AssertException.class)
    public void assertActivityFailTest() {
        appiumDriver.assertView("errorActivity");
    }

    @Test
    public void assertActivityTest() {
        appiumDriver.assertView("TaskList");
    }

    @Test
    public void parseActivityNameTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = AppiumDriver.class.getDeclaredMethod("parseActivityName", InputStream.class);
        method.setAccessible(true);
        String input = "\" Run #8: ActivityRecord{1801877 u0 org.dmfs.tasks/.EditTaskActivity t19018}\\n\" +\n";
        InputStream targetStream =
                new ReaderInputStream(CharSource.wrap(input).openStream(), StandardCharsets.UTF_8.name());
        Object[] arguments =new Object[]{targetStream};
        Assert.assertEquals("EditTaskActivity",method.invoke(appiumDriver, arguments));
    }

    @Test
    public void pressPercentage() {
        appiumDriver.waitAndClickElement("//*[@class='android.widget.ImageButton']");
        appiumDriver.waitFor(3000);
        appiumDriver.pressPercentage("//*[@index='10']//android.widget.SeekBar", 80);
        appiumDriver.waitFor(3000);
    }

    @Test
    public void pauseAndResume() {
        // Click float add button
        appiumDriver.waitAndClickElement("//*[@class='android.widget.ImageButton']");

        appiumDriver.pauseApp();
        appiumDriver.reopenApp();

        appiumDriver.waitAndTypeText("//*[@index='0' and contains(@class, 'android.widget.EditText')]", "Title");
        appiumDriver.waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/editor_action_save']");

        appiumDriver.pauseApp();
        appiumDriver.reopenApp();

        appiumDriver.waitAndClickElement("//*[@class='android.widget.ImageButton']");
        appiumDriver.waitAndClickElement("//*[@class='android.widget.TextView' and @text='My tasks']");
        appiumDriver.assertExist("//*[@class='android.widget.LinearLayout' and ./android.widget.TextView[@text='Title']]");
    }
}