package useCase.command;

import adapter.device.DeviceDriver;

public abstract class Command {
    protected DeviceDriver deviceDriver;
    protected String xPath;

    public Command(DeviceDriver deviceDriver, String xPath) {
        this.deviceDriver = deviceDriver;
        this.xPath = xPath;
    }

    public String getXpath() {
        return xPath;
    }

    abstract void execute();
}
