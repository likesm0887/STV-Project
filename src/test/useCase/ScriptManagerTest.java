package useCase;

import adapter.device.DeviceDriver;
import entity.Config;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
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
            oneOf(mockDriver).launchApp();
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.TextView' and @text='list']");
            oneOf(mockDriver).waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.ImageButton']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
            oneOf(mockDriver).waitAndTypeText("//*[@resource-id='android:id/input']", "456 ");
            oneOf(mockDriver).stopApp();
            oneOf(mockDriver).restartAppAndCleanData();
        }});

        sm.execute();
        System.out.print(sm.summary());
    }

    @Test
    public void formatInformationAfterExecuteScript() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).launchApp();
            will(throwException(new AssertFailedException("you cannot withdraw nothing!")));
        }});

        sm.execute();
    }


    private class AssertFailedException extends RuntimeException {
        public AssertFailedException(String s) {
            super(s);
        }
    }
}
