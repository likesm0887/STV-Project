package useCase.command;

import adapter.device.DeviceDriver;

public abstract class Command implements ICommand {
    protected DeviceDriver deviceDriver;
    protected String xPath;

    public Command(DeviceDriver deviceDriver, String xPath) {
        this.deviceDriver = deviceDriver;
        this.xPath = xPath;
    }

    public String getXpath() {
        return xPath;
    }

    public abstract void execute();
}
