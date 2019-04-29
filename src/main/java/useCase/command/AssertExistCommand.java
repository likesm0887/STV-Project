package useCase.command;

import adapter.device.DeviceDriver;
import entity.Exception.AssertException;

public class AssertExistCommand extends Command {

    public AssertExistCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        deviceDriver.assertExist(xPath);
    }
}
