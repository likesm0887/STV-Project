package useCase.command;

import adapter.device.DeviceDriver;

public class AssertExistCommand extends Command {

    public AssertExistCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        deviceDriver.assertExist(xPath);
    }
}
