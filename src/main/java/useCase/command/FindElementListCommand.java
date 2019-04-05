package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.MobileElement;

import java.util.List;

public class FindElementListCommand extends Command {

    public FindElementListCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        List<MobileElement> mobileElements = this.deviceDriver.findElements(xPath);
    }
}
