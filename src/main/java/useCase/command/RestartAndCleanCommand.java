package useCase.command;

import adapter.device.DeviceDriver;

public class RestartAndCleanCommand extends Command {
    public RestartAndCleanCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.restartAppAndCleanData();
    }
}
