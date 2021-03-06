package useCase.command;

import adapter.device.DeviceDriver;

public class ClickCommand extends Command {
    public ClickCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        this.deviceDriver.waitAndClickElement(xPath);
    }
}
