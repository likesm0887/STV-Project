package useCase.command;

import adapter.device.DeviceDriver;
import org.openqa.selenium.ScreenOrientation;

public class RotateCommand extends Command {
    ScreenOrientation screenOrientation;

    public RotateCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.rotate();
    }
}
