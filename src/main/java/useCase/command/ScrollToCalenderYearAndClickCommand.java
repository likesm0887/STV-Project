package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

import java.util.Calendar;

public class ScrollToCalenderYearAndClickCommand extends Command {
    private SwipeElementDirection direction;

    public ScrollToCalenderYearAndClickCommand(DeviceDriver deviceDriver, String xPath, String direction) {
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
        this.deviceDriver.waitAndClickElement(xPath);
    }
}
