package command;

import adapter.DeviceDriver;
import org.openqa.selenium.ScreenOrientation;

public class RotationCommand extends Command {
    ScreenOrientation screenOrientation;

    public RotationCommand(DeviceDriver deviceDriver, ScreenOrientation screenOrientation) {
        super(deviceDriver, "");
        this.screenOrientation = screenOrientation;
    }

    @Override
    void execute() {
        this.deviceDriver.rotate(this.screenOrientation);
    }
}
