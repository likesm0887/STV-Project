package deviceDriver;

import adapter.device.AppiumDriver;
import adapter.ConfigReader;
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
import static org.junit.Assert.fail;

public class ControllerTest {
    private static AppiumDriverLocalService service;
    private static AppiumDriver adapter;
    private static AndroidDriver driver;

    @BeforeClass
    public static void setUpClass() throws IOException, InterruptedException {
        ConfigReader configReader = new ConfigReader();
        adapter = new AppiumDriver(configReader.getConfig());
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

    @Test
    public void settingTest(){
//        XPathBuilder builder = new XPathBuilder();
//        builder.append("android.support.v7.widget.LinearLayoutCompat")
//                .at(NodeAttribute.INDEX, "1");
//        adapter.clickElement(builder.toString());
        MobileElement elementTwo = (MobileElement) driver.findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat']"));
        elementTwo.click();
    }

    @Test
    public void folderListTest(){
//        XPathBuilder builder = new XPathBuilder();
//        builder.append("android.widget.RelativeLayout")
//                .at(NodeAttribute.INDEX, "0");
//        adapter.clickElement(builder.toString());
//        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@class='android.widget.RelativeLayout' and @index='0']"));
        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@text='My tasks']"));
        element.click();
    }

    @Test
    public void folderListWithTaskTest(){
        MobileElement folder = (MobileElement) driver.findElement(By.xpath("//*[@text='My tasks']"));
        folder.click();

        // add 1st task
        MobileElement quickAdd = (MobileElement) driver.findElement(By.xpath("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']"));
        quickAdd.click();

        adapter.waitFor(1000);


        MobileElement taskName = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/input']"));
        taskName.sendKeys("quick add");

        MobileElement addTask = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/button1']"));
        addTask.click();

        adapter.waitFor(1000);

        // add 2nd task
        MobileElement quickAdd1 = (MobileElement) driver.findElement(By.xpath("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']"));
        quickAdd1.click();

        adapter.waitFor(1000);
        taskName = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/input']"));
        taskName.sendKeys("quick add2");
        addTask = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/button1']"));
        addTask.click();
        adapter.waitFor(1000);

        // click task
        MobileElement task = (MobileElement) driver.findElement(By.xpath("//*[@text='quick add']"));
        task.click();

    }

    @Test
    public void tabTest() {
        MobileElement elementTwo = (MobileElement) driver.findElement(By.xpath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']"));
        elementTwo.click();
    }

    @Test
    public void floatAddTaskTest() {
        MobileElement elementTwo = (MobileElement) driver.findElement(By.xpath("//*[@class='android.widget.ImageButton']"));
        elementTwo.click();
    }

    @Test
    public void quickAddTest(){
        // select folder
        MobileElement folderList = (MobileElement) adapter.waitForElement("//*[@class='android.widget.RelativeLayout' and @index='0']");
        folderList.click();
        // click quick add
        MobileElement quickAdd = (MobileElement) adapter.waitForElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        quickAdd.click();
        // edit button
//        MobileElement editBtn = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/edit']"));
//        editBtn.click();
        // spinner(select folder)
        MobileElement spinner = (MobileElement) adapter.waitForElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
        spinner.click();
        // spinner options
        MobileElement options = (MobileElement) adapter.waitForElement("//*[@index='0' and @class='android.widget.LinearLayout']");
        options.click();
        // edit text
        MobileElement editText = (MobileElement) adapter.waitForElement("//*[@resource-id='android:id/input']");
        editText.sendKeys("Test");
        // save and continue
        MobileElement saveAndContinueBtn = (MobileElement) adapter.waitForElement("//*[@resource-id='android:id/button2']");
        saveAndContinueBtn.click();
        // save
        MobileElement saveBtn = (MobileElement) adapter.waitForElement("//*[@resource-id='android:id/button1']");
        saveBtn.click();
    }

    @Test
    public void createTaskTest(){
        // create task
        MobileElement createTask = (MobileElement) driver.findElement(By.xpath("//*[@class='android.widget.ImageButton']"));
        createTask.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // spinner
        MobileElement spinner = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']"));
        spinner.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // options
        MobileElement options = (MobileElement) driver.findElement(By.xpath("//*[@index='0' and @class='android.widget.LinearLayout']"));
        options.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // input title
//        MobileElement editTitle = (MobileElement) driver.findElement(By.xpath("//*[@index='0']/android.widget.EditText"));
        MobileElement editTitle = (MobileElement) driver.findElement(By.xpath("//*[@index='0' and contains(@class, 'android.widget.EditText')]"));
        editTitle.sendKeys("Test");
        // select status
        MobileElement status = (MobileElement) driver.findElement(By.xpath("//*[@index='1' and contains(@class, 'android.widget.Spinner')]"));
        status.click();
        // status options
//        MobileElement statusOptions = (MobileElement) driver.findElement(By.xpath("//*[@index='1' and @class='android.widget.LinearLayout']"));
        MobileElement statusOptions = (MobileElement) driver.findElement(By.xpath("//*[@text='in process']"));
        statusOptions.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // save
        MobileElement saveBtn = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='org.dmfs.tasks:id/editor_action_save']"));
        saveBtn.click();
    }

    @Test
    public void inputValueTest(){
        // create task
        MobileElement createTask = (MobileElement) driver.findElement(By.xpath("//*[@class='android.widget.ImageButton']"));
        createTask.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // location
        MobileElement editLocation = (MobileElement) driver.findElement(By.xpath("//*[@index='2' and contains(@class, 'android.widget.EditText')]"));

        editLocation.sendKeys("Test location");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ExpandableListView")
                .at(NodeAttribute.RESOURCE_ID, "android:id/list");
        MobileElement element = adapter.findElement(builder.toString());
        element.swipe(SwipeElementDirection.UP, 100, 100, 1000);

        // description
        MobileElement editDescription = (MobileElement) driver.findElement(By.xpath("//*[@index='3' and contains(@class, 'android.widget.EditText')]"));
        editDescription.sendKeys("Test description");
    }
}