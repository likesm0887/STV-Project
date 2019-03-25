package activity;

import adapter.AppiumAdapter;
import config.ConfigReader;
import entity.xPath.XPathBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class mainActivityTest {
    protected Activity mainActivity;
    protected AppiumAdapter appiumAdapter;
    protected XPathBuilder xPathBuilder;

    @Before
    public void setUp() throws IOException, InterruptedException {
        ConfigReader configReader = new ConfigReader();
        xPathBuilder = new XPathBuilder();
        appiumAdapter = new AppiumAdapter(configReader.getConfig());
        mainActivity = new Activity(appiumAdapter);
    }

    @After
    public void tearDown() {
        appiumAdapter.closeAppiumService();
    }

    @Test
    public void clickCreateNewTaskButton() {
        mainActivity.launchApp();

        mainActivity.clickElement(MainActivity.ADD_NEW_TASK_BUTTON);
    }

    @Test
    public void clickTab() {
        int FIRST_TAB = 0;
        mainActivity.launchApp();

        mainActivity.clickTabBar(MainActivity.TAB_BAR, FIRST_TAB);
    }

    @Test
    public void clickAllTab() {
        mainActivity.launchApp();

        mainActivity.clickAllTabBar(MainActivity.TAB_BAR);
    }

    mainActivity click TASK_LABEL



    class Command {
        void execute() {

        }
    }



    class ClickCommand extends Command {
        ClickCommand(ActivityAttribute activityAttribute) {

        }

        void execute() {
            mainActivity.clickElement(mainActivity);
        }
    }


    @Test
    public void clickTaskLabel() {
        mainActivity.launchApp();

        mainActivity.clickElement(MainActivity.TASK_LABEL);

        mainActivity.startWaiting();

        mainActivity.clickElement(MainActivity.QUICK_ADD_TASK_BUTTON);

        mainActivity.startWaiting();

        mainActivity.typeText(MainActivity.QUICK_ADD_TASK_TEXTFIELD, "TEST");

        mainActivity.clickElement(MainActivity.QUICK_ADD_TASK_SAVE_BUTTON);
    }

    @Test
    public void clickTaskLabelTwice() {
        mainActivity.launchApp();

        mainActivity.clickElement(MainActivity.TASK_LABEL);

        mainActivity.startWaiting();

        mainActivity.clickElement(MainActivity.TASK_LABEL);
    }

    @Test
    public void clickSettingButton() {
        mainActivity.launchApp();

        mainActivity.clickElement(MainActivity.SETTING_BUTTON);
    }
}