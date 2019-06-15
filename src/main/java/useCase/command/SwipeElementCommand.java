package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class SwipeElementCommand extends Command {
    private SwipeElementDirection swipeElementDirection;
    private int offset;

    public SwipeElementCommand(DeviceDriver deviceDriver, String xPath, String direction) {
        super(deviceDriver, xPath);
        this.swipeElementDirection = convertDirection(direction);
        this.offset = 10;
    }

    @Override
    public void execute() {
        this.deviceDriver.waitAndSwipeElement(this.xPath, this.swipeElementDirection, this.offset);
    }

    private SwipeElementDirection convertDirection(String direction) {
        return SwipeElementDirection.valueOf(direction.toUpperCase());
    }
}
