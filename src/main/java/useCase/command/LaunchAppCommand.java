package useCase.command;

import adapter.device.DeviceDriver;

public class LaunchAppCommand extends Command {

    public LaunchAppCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        deviceDriver.launchApp();
    }
}
