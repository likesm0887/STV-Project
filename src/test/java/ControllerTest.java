import adapter.AppiumAdapter;
import adapter.ScreenSize;
import entity.xPath.NodeAttribute;
import entity.xPath.XPathBuilder;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ControllerTest {
    private static final int SERVER_PORT = 50000;
    private static AppiumDriverLocalService service;
    private static AppiumAdapter adapter;
    private static AndroidDriver driver;

    @BeforeClass
    public static void setUpClass() {
        setUpAppiumService();
        adapter = new AppiumAdapter();
        driver = adapter.getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
        service.stop();
    }

    @Before
    public void setUp() {
//        driver.resetApp();
        driver.launchApp();
    }

//    @After
//    public void tearDown() throws Exception {
//        driver.closeApp();
//    }

    private static void setUpAppiumService() {
        service = new AppiumServiceBuilder().usingPort(SERVER_PORT).build();
        service.start();
    }

    @Test
    public void environmentTest() {
        MobileElement elementTwo = (MobileElement) driver.findElementByClassName("android.widget.ImageView");
        elementTwo.click();
    }

    @Test
    public void builder() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .at(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");

        adapter.clickElement(builder.toString());
    }

    @Test
    public void switchTabs() {
        List<MobileElement> tabs = driver.findElements(By.className("android.support.v7.app.ActionBar$Tab"));
        tabs.forEach(each -> each.click());
    }

    @Test
    public void clickTab() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("*")
                .at(NodeAttribute.CLASS, "android.support.v7.app.ActionBar$Tab")
                .and(NodeAttribute.INDEX, "3");
        adapter.clickElement(builder.toString());
    }

    @Test
    public void scrolling() {
        ScreenSize size = adapter.getScreenSize();
        int y = (int) (size.getHeight() * 0.5);
        int fromX = 1059;
        int toX = 105;
        for (int i = 0; i < 6; i++)
            driver.swipe(fromX, y, toX, y, 500);
//            adapter.scrollHorizontal(y, fromX, toX);
    }

    @Test
    public void quicklyAddTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.TextView")
                .at(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "My tasks");
        adapter.clickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.ImageView")
                .at(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/quick_add_task")
                .and(NodeAttribute.INDEX, "2");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);

        builder.clean();
        builder.append("android.widget.EditText")
                .at(NodeAttribute.RESOURCE_ID, "android:id/input")
                .and(NodeAttribute.TEXT, "Title");
        adapter.sendKeysAtElement(builder.toString(), "quick add");

        builder.clean();
        builder.append("android.widget.TextView")
                .at(NodeAttribute.RESOURCE_ID, "android:id/button1")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.clickElement(builder.toString());

        adapter.waitFor(2000);
        builder.clean();
        builder.append("android.widget.TextView")
                .at(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "quick add");
        List<MobileElement> elements = adapter.findElements(builder.toString());
        assertEquals(1, elements.size());
        assertEquals("quick add", elements.get(0).getText());
    }

    @Test
    public void addTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .at(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);
        builder.clean();
        builder.append("android.widget.EditText")
                .at(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.TEXT, "Title");
        adapter.sendKeysAtElement(builder.toString(), "New Task");

        adapter.clickBack();

        builder.clean();
        builder.append("android.widget.EditText")
                .at(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.INDEX, "1");

        adapter.sendKeysAtElement(builder.toString(), "todo 1");

        builder.clean();
        builder.append("android.widget.TextView")
                .at(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/editor_action_save")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);
        builder.clean();
        builder.append("android.widget.TextView")
                .at(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/text")
                .and(NodeAttribute.INDEX, "0");

        MobileElement element = adapter.findElement(builder.toString());
        assertEquals("New Task", element.getText());
    }

    @Test
    public void swipTest() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ExpandableListView")
                .at(NodeAttribute.RESOURCE_ID, "android:id/list");
        MobileElement element = adapter.findElement(builder.toString());
        element.swipe(SwipeElementDirection.LEFT, 100, 100, 1000);
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }
}