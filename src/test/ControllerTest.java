import adapter.AppiumAdapter;
import config.ConfigReader;
import entity.xPath.NodeAttribute;
import entity.xPath.XPathBuilder;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ControllerTest {
    private static AppiumDriverLocalService service;
    private static AppiumAdapter adapter;
    private static AndroidDriver driver;

    @BeforeClass
    public static void setUpClass() throws IOException, InterruptedException {
        ConfigReader configReader = new ConfigReader();
        adapter = new AppiumAdapter(configReader.getConfig());
        driver = adapter.createAndroidDriver();
    }
/*
    @AfterClass
    public static void tearDownClass() {
        service.stop();
    }*/

    @Before
    public void setUp() {
//        driver.resetApp();
        driver.launchApp();
    }

//    @After
//    public void tearDown() throws Exception {
//        driver.closeApp();
//    }

    @Test
    public void environmentTest() {
        MobileElement elementTwo = (MobileElement) driver.findElementByClassName("android.widget.ImageView");
        elementTwo.click();
    }

    @Test
    public void builder() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");

        adapter.clickElement(builder.toString());
    }

    @Test
    public void switchTabs() {
        driver.launchApp();
        List<MobileElement> tabs = driver.findElements(By.className("android.support.v7.app.ActionBar$Tab"));
        tabs.forEach(each -> each.click());
    }

    @Test
    public void clickTab() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("*")
                .which(NodeAttribute.CLASS, "android.support.v7.app.ActionBar$Tab")
                .and(NodeAttribute.INDEX, "3");
        adapter.clickElement(builder.toString());
    }

    @Test
    public void quicklyAddTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "My tasks");
        adapter.clickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.ImageView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/quick_add_task")
                .and(NodeAttribute.INDEX, "2");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/input")
                .and(NodeAttribute.TEXT, "Title");
        adapter.typeText(builder.toString(), "quick add");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/button1")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.clickElement(builder.toString());

        adapter.waitFor(2000);
        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "quick add");
        List<MobileElement> elements = adapter.findElements(builder.toString());
        assertEquals(1, elements.size());
        assertEquals("quick add", elements.get(0).getText());
    }

    @Test
    public void addTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);
        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.TEXT, "Title");
        adapter.typeText(builder.toString(), "New Task");

        adapter.pressBackKey();

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.INDEX, "1");

        adapter.typeText(builder.toString(), "todo 1");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/editor_action_save")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.clickElement(builder.toString());

        adapter.waitFor(1000);
        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/text")
                .and(NodeAttribute.INDEX, "0");

        MobileElement element = adapter.findElement(builder.toString());
        assertEquals("New Task", element.getText());
    }

    @Test
    public void swipTest() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ExpandableListView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/list");
        MobileElement element = adapter.findElement(builder.toString());
        element.swipe(SwipeElementDirection.LEFT, 100, 100, 1000);
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }
}