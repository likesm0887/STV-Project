package command;

import adapter.DeviceDriver;

public abstract class Command {

    protected DeviceDriver deviceDriver;
    protected String xPath;

    public Command(DeviceDriver deviceDriver, String xPath) {
        this.deviceDriver = deviceDriver;
        this.xPath = xPath;
    }

    abstract void execute();

}
