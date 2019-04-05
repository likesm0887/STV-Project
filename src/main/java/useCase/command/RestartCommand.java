package useCase.command;

import adapter.device.DeviceDriver;

public class RestartCommand extends Command {
    public RestartCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }


    @Override
    public void execute() {
        this.deviceDriver.restartApp("CleanApp");
    }
}
