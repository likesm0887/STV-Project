package command;

import adapter.DeviceDriver;

public class CommandFactory {
    private DeviceDriver deviceDriver;
    public CommandFactory(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }


    public Command createClickCommand(String xPath) {
        return new ClickCommand(this.deviceDriver, xPath);
    }

    public Command createTypeTextCommand(String xPath, String text) {
        return new TypeTextCommand(this.deviceDriver, xPath, text);
    }

    public Command createRestartCommand() {
        return null;
    }
}
