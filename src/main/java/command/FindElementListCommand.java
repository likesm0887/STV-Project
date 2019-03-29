package command;

import adapter.DeviceDriver;
import io.appium.java_client.MobileElement;

import java.util.List;

public class FindElementListCommand extends Command {

    public FindElementListCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    void execute() {
        List<MobileElement> mobileElements = this.deviceDriver.findElements(xPath);
    }
}
