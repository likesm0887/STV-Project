package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class SelectTimeZoneCommand extends Command {
    private SwipeElementDirection direction;

    public SelectTimeZoneCommand(DeviceDriver deviceDriver, String xPath, String direction) {
        super(deviceDriver, xPath);
        this.direction = SwipeElementDirection.valueOf(direction.toUpperCase());
    }

    @Override
    public void execute() {
        this.deviceDriver.waitAndScrollToElement(xPath, direction);
        this.deviceDriver.waitAndClickElement(xPath);
    }
}
