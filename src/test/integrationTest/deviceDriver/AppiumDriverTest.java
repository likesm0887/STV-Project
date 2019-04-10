package integrationTest.deviceDriver;

import adapter.device.AppiumDriver;
import adapter.ConfigReader;
import entity.xPath.NodeAttribute;
import entity.xPath.XPathBuilder;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppiumDriverTest {
    private static AppiumDriver driver;
    private static AndroidDriver androidDriver;

    @BeforeClass
    public static void setUpClass() {
        ConfigReader configReader = new ConfigReader();
        driver = new AppiumDriver(configReader.getConfig());
        driver.startAppiumService();
        androidDriver = driver.getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.stopAppiumService();
    }

    @Before
    public void setUp() {
        driver.restartAppAndCleanData();
    }

    @Test
    public void environmentTest() {
        MobileElement elementTwo = (MobileElement) androidDriver.findElementByClassName("android.widget.ImageView");
        elementTwo.click();
    }

    @Test
    public void builder() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");

        driver.clickElement(builder.toString());
    }

    @Test
    public void switchTabs() {
        List<MobileElement> tabs = androidDriver.findElements(By.className("android.support.v7.app.ActionBar$Tab"));
        tabs.forEach(each -> each.click());
    }

    @Test
    public void clickTab() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("*")
                .which(NodeAttribute.CLASS, "android.support.v7.app.ActionBar$Tab")
                .and(NodeAttribute.INDEX, "3");
        driver.clickElement(builder.toString());
    }

    @Test
    public void quicklyAddTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "My tasks");
        driver.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.ImageView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/quick_add_task")
                .and(NodeAttribute.INDEX, "2");
        driver.waitAndClickElement(builder.toString());

        driver.waitFor(1000);

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/input")
                .and(NodeAttribute.TEXT, "Title");
        driver.waitAndTypeText(builder.toString(), "quick add");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/button1")
                .and(NodeAttribute.TEXT, "SAVE");
        driver.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/title")
                .and(NodeAttribute.TEXT, "quick add");
        List<MobileElement> elements = driver.waitForElements(builder.toString());
        assertEquals(1, elements.size());
        assertEquals("quick add", elements.get(0).getText());
    }

    @Test
    public void addTask() {
        XPathBuilder builder = new XPathBuilder();
        builder.append("android.widget.ImageButton")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/floating_action_button")
                .and(NodeAttribute.INDEX, "2");
        driver.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.TEXT, "Title");
        driver.waitAndTypeText(builder.toString(), "New Task");

        driver.pressBackKey();

        builder.clean();
        builder.append("android.widget.EditText")
                .which(NodeAttribute.RESOURCE_ID, "android:id/text1")
                .and(NodeAttribute.INDEX, "1");

        driver.waitAndTypeText(builder.toString(), "todo 1");

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/editor_action_save")
                .and(NodeAttribute.TEXT, "SAVE");
        driver.waitAndClickElement(builder.toString());

        builder.clean();
        builder.append("android.widget.TextView")
                .which(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/text")
                .and(NodeAttribute.INDEX, "0");

        MobileElement element = driver.waitForElement(builder.toString());
        assertEquals("New Task", element.getText());
    }

    @Test
    public void menuTest(){
        MobileElement elementTwo =  driver.waitForElement("//*[@class='android.support.v7.widget.LinearLayoutCompat']");
        elementTwo.click();
    }

    @Test
    public void folderListTest(){
        MobileElement element =  driver.waitForElement("//*[@text='My tasks']");
        element.click();
    }

    @Test
    public void folderListWithTaskTest(){
        MobileElement folder =  driver.waitForElement("//*[@text='My tasks']");
        folder.click();

        // add 1st task
        MobileElement quickAdd = driver.waitForElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        quickAdd.click();

        driver.waitFor(1000);


        MobileElement taskName = driver.waitForElement("//*[@resource-id='android:id/input']");
        taskName.sendKeys("quick add");

        MobileElement addTask = driver.waitForElement("//*[@resource-id='android:id/button1']");
        addTask.click();

        driver.waitFor(1000);

        // add 2nd task
        MobileElement quickAdd1 =  driver.waitForElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        quickAdd1.click();

        driver.waitFor(1000);
        taskName =  driver.waitForElement("//*[@resource-id='android:id/input']");
        taskName.sendKeys("quick add2");
        addTask =  driver.waitForElement("//*[@resource-id='android:id/button1']");
        addTask.click();
        driver.waitFor(1000);

        // click task
        MobileElement task = (MobileElement) driver.waitForElement("//*[@text='quick add']");
        task.click();

    }

    @Test
    public void tabTest() {
        MobileElement elementTwo =(MobileElement) driver.waitForElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
        elementTwo.click();
    }

    @Test
    public void floatAddTaskTest() {
        MobileElement elementTwo = (MobileElement) driver.waitForElement("//*[@class='android.widget.ImageButton']");
        elementTwo.click();
    }

    @Test
    public void quickAddTest(){
        // select folder
        driver.waitAndClickElement("//*[@class='android.widget.RelativeLayout' and @index='0']");

        // click quick add
        driver.waitAndClickElement("//*[@index='0']/android.widget.ImageView[@resource-id='org.dmfs.tasks:id/quick_add_task']");
        // edit button
//        MobileElement editBtn = (MobileElement) androidDriver.findElement(By.xpath("//*[@resource-id='android:id/edit']"));
//        editBtn.click();
        // spinner(select folder)
        driver.waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
        // spinner options
        driver.waitAndClickElement("//*[@index='0' and @class='android.widget.LinearLayout']");
        // edit text
        MobileElement editText = (MobileElement) driver.waitForElement("//*[@resource-id='android:id/input']");
        editText.sendKeys("Test");
        // save and continue
        driver.waitAndClickElement("//*[@resource-id='android:id/button2']");
        // save
        driver.waitAndClickElement("//*[@resource-id='android:id/button1']");
    }

    @Test
    public void createTaskTest(){
        // create task
        MobileElement createTask = driver.waitForElement(("//*[@class='android.widget.ImageButton']"));
        createTask.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // spinner
        MobileElement spinner = driver.waitForElement(("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']"));
        spinner.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // options
        MobileElement options = driver.waitForElement("//*[@index='0' and @class='android.widget.LinearLayout']");
        options.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // input title
//        MobileElement editTitle = (MobileElement) androidDriver.findElement(By.xpath("//*[@index='0']/android.widget.EditText"));
        MobileElement editTitle = driver.waitForElement("//*[@index='0' and contains(@class, 'android.widget.EditText')]");
        editTitle.sendKeys("Test");
        // select status
        MobileElement status = driver.waitForElement("//*[@index='1' and contains(@class, 'android.widget.Spinner')]");
        status.click();
        // status options
//        MobileElement statusOptions = (MobileElement) androidDriver.findElement(By.xpath("//*[@index='1' and @class='android.widget.LinearLayout']"));
        MobileElement statusOptions = driver.waitForElement("//*[@text='in process']");
        statusOptions.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // save
        MobileElement saveBtn = driver.waitForElement("//*[@resource-id='org.dmfs.tasks:id/editor_action_save']");
        saveBtn.click();
    }

    @Test
    public void inputValueTest(){
        // create task

        driver.waitAndClickElement("//*[@class='android.widget.ImageButton']");
        // location
        driver.waitAndTypeText( "//*[@index='2']/android.widget.EditText","Test location");

        XPathBuilder builder = new XPathBuilder();
        /*builder.append("android.widget.ExpandableListView")
                .which(NodeAttribute.RESOURCE_ID, "android:id/list");
       driver.swipeElement(builder.toString(),SwipeElementDirection.DOWN,10);
*/

        // description
        driver.waitAndTypeText("//*[@index='3']/android.widget.EditText","Test description");
    }
}