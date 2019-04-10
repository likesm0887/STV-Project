package useCase;

import adapter.device.DeviceDriver;
import entity.Config;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScriptManagerTest {
    private final String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private String SIMPLE_SCRIPT = "./src/test/resources/scriptForMapperTest.txt";
    private ScriptManager sm;
    Mockery context = new JUnit4Mockery();
    DeviceDriver mockDriver;

    @Before
    public void setUp() {
        try {
            mockDriver = context.mock(DeviceDriver.class);
            Config config = new Config("", "", 0, 0, SIMPLE_TEST_DATA, SIMPLE_SCRIPT);
            sm = new ScriptManager(config, mockDriver);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getAllPathTest() {
        List<String> allPath = null;
        try {
            allPath = sm.getAllFilesPath("./src/test/resources");
        } catch (IOException e) {
            Assert.fail();
        }
        assertEquals(allPath.size(), 5);
    }

    @Test
    public void executeScriptTest() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.RelativeLayout' and @index='0']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.view.View' and @text='123']");
            oneOf(mockDriver).waitAndClickElement("//*[@resource-id='android:id/button1']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
            oneOf(mockDriver).waitAndTypeText("//*[@class='android.widget.RelativeLayout' and @index='123']", "456 ");
            oneOf(mockDriver).restartAppAndCleanData();
        }});

        sm.execute();
    }
}
