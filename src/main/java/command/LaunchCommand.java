package command;

import adapter.DeviceDriver;

public class LaunchCommand extends Command {

    public LaunchCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    void execute() {
        deviceDriver.launchApplication();
    }
}
