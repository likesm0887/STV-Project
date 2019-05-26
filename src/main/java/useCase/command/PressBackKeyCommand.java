package useCase.command;

import adapter.device.DeviceDriver;

public class PressBackKeyCommand extends Command {
    public PressBackKeyCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.pressBackKey();
    }
}
