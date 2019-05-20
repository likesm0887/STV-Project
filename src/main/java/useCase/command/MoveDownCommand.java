package useCase.command;

import adapter.device.DeviceDriver;

public class MoveDownCommand extends Command {
    public MoveDownCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        deviceDriver.waitAndDragElement(xPath, 0, 100);
    }
}
