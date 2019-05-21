package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class ScrollToElementCommand extends Command {
    private SwipeElementDirection direction;

    public ScrollToElementCommand(DeviceDriver deviceDriver, String xPath, String direction) {
        super(deviceDriver, xPath);
        try {
            this.direction = SwipeElementDirection.valueOf(direction.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void execute() {
        this.deviceDriver.waitAndScrollToElement(xPath, direction);
    }
}
