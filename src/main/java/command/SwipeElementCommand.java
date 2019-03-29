package command;

import adapter.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class SwipeElementCommand extends Command {
    SwipeElementDirection swipeElementDirection;

    public SwipeElementCommand(DeviceDriver deviceDriver,
                               String xPath,
                               SwipeElementDirection swipeElementDirection) {
        super(deviceDriver, xPath);
        this.swipeElementDirection = swipeElementDirection;
    }

    @Override
    void execute() {
        this.deviceDriver.swipeElement(this.xPath, this.swipeElementDirection);
    }
}
