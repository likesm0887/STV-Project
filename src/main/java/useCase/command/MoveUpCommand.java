package useCase.command;

import adapter.device.DeviceDriver;

public class MoveUpCommand extends Command {
    int offset = 10;

    public MoveUpCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        deviceDriver.waitAndDragElement(xPath, 0, -100);
    }
}
