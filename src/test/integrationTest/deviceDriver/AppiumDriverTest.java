package deviceDriver;

import adapter.device.AppiumDriver;
import adapter.ConfigReader;
import entity.xPath.NodeAttribute;
import entity.xPath.XPathBuilder;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AppiumDriverTest {
    private static AppiumDriver adapter;
    private static AndroidDriver driver;

    @BeforeClass
    public static void setUpClass() {
        ConfigReader configReader = new ConfigReader();
        adapter = new AppiumDriver(configReader.getConfig());
        driver = adapter.getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
        adapter.restartApp("CleanApp");
    }

    @Before
    public void setUp() {
        adapter.restartApp();
        adapter.waitFor(1000);
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
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
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
        adapter.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.ImageView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/quick_add_task")
                .and(NodeAttribute.INDEX, "2");
        adapter.waitAndClickElement(builder.toString());

        adapter.waitFor(1000);

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/input")
                .and(NodeAttribute.TEXT, "Title");
        adapter.waitAndTypeText(builder.toString(), "quick add");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/button1")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "quick add");
        List<MobileElement> elements = adapter.waitForElements(builder.toString());
        assertEquals(1, elements.size());
        assertEquals("quick add", elements.get(0).getText());
    }

    @Test
    public void addTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");
        adapter.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.TEXT, "Title");
        adapter.waitAndTypeText(builder.toString(), "New Task");

        adapter.pressBackKey();

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.INDEX, "1");

        adapter.waitAndTypeText(builder.toString(), "todo 1");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/editor_action_save")
                .and(NodeAttribute.TEXT, "SAVE");
        adapter.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/text")
                .and(NodeAttribute.INDEX, "0");

        MobileElement element = adapter.waitForElement(builder.toString());
        assertEquals("New Task", element.getText());
    }

    @Test
    public void settingTest(){
//        XPathBuilder builder = new XPathBuilder();
//        builder.append("android.support.v7.widget.LinearLayoutCompat")
//                .at(NodeAttribute.INDEX, "1");
//        adapter.clickElement(builder.toString());
        MobileElement elementTwo =  adapter.waitForElement("//*[@class='android.support.v7.widget.LinearLayoutCompat']");
        elementTwo.click();
    }

    @Test
    public void folderListTest(){
//        XPathBuilder builder = new XPathBuilder();
//        builder.append("android.widget.RelativeLayout")
//                .at(NodeAttribute.INDEX, "0");
//        adapter.clickElement(builder.toString());
//        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@class='android.widget.RelativeLayout' and @index='0']"));
        MobileElement element =  adapter.waitForElement("//*[@text='My tasks']");
        element.click();
    }

    @Test
    public void folderListWithTaskTest(){
        MobileElement folder =  adapter.waitForElement("//*[@text='My tasks']");
        folder.click();

        // add 1st task
        MobileElement quickAdd = adapter.waitForElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        quickAdd.click();

        adapter.waitFor(1000);


        MobileElement taskName = adapter.waitForElement("//*[@resource-id='android:id/input']");
        taskName.sendKeys("quick add");

        MobileElement addTask = adapter.waitForElement("//*[@resource-id='android:id/button1']");
        addTask.click();

        adapter.waitFor(1000);

        // add 2nd task
        MobileElement quickAdd1 =  adapter.waitForElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        quickAdd1.click();

        adapter.waitFor(1000);
        taskName =  adapter.waitForElement("//*[@resource-id='android:id/input']");
        taskName.sendKeys("quick add2");
        addTask =  adapter.waitForElement("//*[@resource-id='android:id/button1']");
        addTask.click();
        adapter.waitFor(1000);

        // click task
        MobileElement task = (MobileElement) adapter.waitForElement("//*[@text='quick add']");
        task.click();

    }

    @Test
    public void tabTest() {
        MobileElement elementTwo =(MobileElement) adapter.waitForElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
        elementTwo.click();
    }

    @Test
    public void floatAddTaskTest() {
        MobileElement elementTwo = (MobileElement) adapter.waitForElement("//*[@class='android.widget.ImageButton']");
        elementTwo.click();
    }

    @Test
    public void quickAddTest(){
        // select folder
        adapter.waitAndClickElement("//*[@class='android.widget.RelativeLayout' and @index='0']");

        // click quick add
        adapter.waitAndClickElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        // edit button
//        MobileElement editBtn = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/edit']"));
//        editBtn.click();
        // spinner(select folder)
        adapter.waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
        // spinner options
        adapter.waitAndClickElement("//*[@index='0' and @class='android.widget.LinearLayout']");
        // edit text
        MobileElement editText = (MobileElement) adapter.waitForElement("//*[@resource-id='android:id/input']");
        editText.sendKeys("Test");
        // save and continue
        adapter.waitAndClickElement("//*[@resource-id='android:id/button2']");
        // save
        adapter.waitAndClickElement("//*[@resource-id='android:id/button1']");
    }

    @Test
    public void createTaskTest(){
        // create task
        MobileElement createTask = adapter.waitForElement(("//*[@class='android.widget.ImageButton']"));
        createTask.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // spinner
        MobileElement spinner = adapter.waitForElement(("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']"));
        spinner.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // options
        MobileElement options = adapter.waitForElement("//*[@index='0' and @class='android.widget.LinearLayout']");
        options.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // input title
//        MobileElement editTitle = (MobileElement) driver.findElement(By.xpath("//*[@index='0']/android.widget.EditText"));
        MobileElement editTitle = adapter.waitForElement("//*[@index='0' and contains(@class, 'android.widget.EditText')]");
        editTitle.sendKeys("Test");
        // select status
        MobileElement status = adapter.waitForElement("//*[@index='1' and contains(@class, 'android.widget.Spinner')]");
        status.click();
        // status options
//        MobileElement statusOptions = (MobileElement) driver.findElement(By.xpath("//*[@index='1' and @class='android.widget.LinearLayout']"));
        MobileElement statusOptions = adapter.waitForElement("//*[@text='in process']");
        statusOptions.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // save
        MobileElement saveBtn =adapter.waitForElement("//*[@resource-id='org.dmfs.tasks:id/editor_action_save']");
        saveBtn.click();
    }

    @Test
    public void inputValueTest(){
        // create task

        adapter.waitAndClickElement("//*[@class='android.widget.ImageButton']");
        // location
        adapter.waitAndTypeText( "//*[@index='2']/android.widget.EditText","Test location");

        XPathBuilder builder = new XPathBuilder();
        /*builder.append("android.widget.ExpandableListView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/list");
       adapter.swipeElement(builder.toString(),SwipeElementDirection.DOWN,10);
*/

        // description
        adapter.waitAndTypeText("//*[@index='3']/android.widget.EditText","Test description");
    }
}