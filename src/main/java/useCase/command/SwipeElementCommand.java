package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class SwipeElementCommand extends Command {
    SwipeElementDirection swipeElementDirection;
    int offset;

    public SwipeElementCommand(DeviceDriver deviceDriver,
                               String xPath,
                               SwipeElementDirection swipeElementDirection, int offset) {
        super(deviceDriver, xPath);
        this.swipeElementDirection = swipeElementDirection;
        this.offset = offset;
    }

    @Override
    public void execute() {
        this.deviceDriver.swipeElement(this.xPath, this.swipeElementDirection, this.offset);
    }
}
