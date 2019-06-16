package useCase.command;

import adapter.device.DeviceDriver;

public class AssertNotExistCommand extends Command {

    public AssertNotExistCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        deviceDriver.assertNotExist(xPath);
    }
}
