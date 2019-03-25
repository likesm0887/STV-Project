package activity;

import adapter.DeviceDriver;
import io.appium.java_client.MobileElement;

import java.util.List;

public class Activity {

    protected DeviceDriver deviceDriver;
    protected boolean isWaiting = false;

    public Activity(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    public void waitUtilCurrentElementDone(String xPath) {
        if (!isWaiting())
            return;

        deviceDriver.waitUntilElementShow(xPath);
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void startWaiting() {
        isWaiting = true;
    }

    public void cancelWaiting() {
        isWaiting = false;
    }

    public void launchApp() {
        this.deviceDriver.launchApplication();
    }

    public void typeText(ActivityAttribute activityAttribute, String text) {
        String xPath = this.fetchXPath(activityAttribute);

        this.waitUtilCurrentElementDone(xPath);

        this.deviceDriver.typeText(xPath, text);

        this.cancelWaiting();
    }

    public void clickElement(ActivityAttribute activityAttribute) {
        String xPath = this.fetchXPath(activityAttribute);

        this.waitUtilCurrentElementDone(xPath);

        this.deviceDriver.clickElement(xPath);

        this.cancelWaiting();
    }

    public void clickTabBar(ActivityAttribute activityAttribute, int index) {
        String xPath = this.fetchXPath(activityAttribute);

        this.waitUtilCurrentElementDone(xPath);

        List<MobileElement> tabList = this.deviceDriver.findElements(xPath);

        tabList.get(index).click();

        this.cancelWaiting();
    }

    public void clickAllTabBar(ActivityAttribute activityAttribute) {
        String xPath = this.fetchXPath(activityAttribute);

        this.waitUtilCurrentElementDone(xPath);

        List<MobileElement> tabList = this.deviceDriver.findElements(xPath);

        for (int i = 0; i < tabList.size(); i++)
            tabList.get(i).click();

        this.cancelWaiting();

    }

    public String fetchXPath(ActivityAttribute activityAttribute) {
        return activityAttribute.getXPath();
    }
}
