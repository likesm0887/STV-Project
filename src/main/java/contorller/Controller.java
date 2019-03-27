package contorller;
import adapter.AppiumAdapter;
import adapter.DeviceDriver;
import config.ConfigReader;
import coverage.CodeCovergerator;
import entity.xPath.NodeAttribute;
import entity.xPath.XPathBuilder;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class Controller {
    private  AppiumDriverLocalService service;
    private  AppiumAdapter adapter;
    private  AndroidDriver driver;

    public void execute() throws IOException, InterruptedException {
        
        ConfigReader configReader = new ConfigReader();
        AppiumAdapter adapter = new AppiumAdapter(configReader.getConfig());
        String[] stopCmd = {"adb","shell", "am", "force-stop", "org.dmfs.tasks"};
        adapter.executeCmd(stopCmd);
        sleep(1000);
        adapter.launchApp();
        sleep(1000);
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
        adapter.sendKeysAtElement(builder.toString(), "quick add");

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
        driver = adapter.getDriver();
        String[] stopTestBroadcastCmd = {"adb","shell", "am", "broadcast", "-a", "\"test\""};
        sleep(10000);
        adapter.executeCmd(stopTestBroadcastCmd);
        sleep(1000);
        CodeCovergerator codeCovergerator = new CodeCovergerator();
        codeCovergerator.PullCodeCoverage(configReader.getConfig());
    }
}
