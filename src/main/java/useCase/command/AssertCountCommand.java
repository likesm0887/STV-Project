package useCase.command;

import adapter.device.DeviceDriver;

public class AssertCountCommand extends Command {
    private int count;

    public AssertCountCommand(DeviceDriver deviceDriver, String xPath, String count) {
        super(deviceDriver, xPath);
        this.count = Integer.parseInt(count);
    }

    @Override
    public void execute() {
        deviceDriver.assertElementCount(xPath, count);
    }
}
