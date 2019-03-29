package command;

import adapter.DeviceDriver;

public class RestartCommand extends Command {
    public RestartCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }


    @Override
    void execute() {
        this.deviceDriver.restartApp();
    }
}
