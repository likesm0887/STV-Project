package useCase.command;

import adapter.device.DeviceDriver;

public class PressEnterCommand extends Command {
    public PressEnterCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.pressEnter();
    }
}
