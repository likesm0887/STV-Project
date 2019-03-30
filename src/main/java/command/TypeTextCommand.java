package command;

import adapter.DeviceDriver;

public class TypeTextCommand extends Command {
    private String text;

    public TypeTextCommand(DeviceDriver deviceDriver, String xPath, String text) {
        super(deviceDriver, xPath);
        this.text = text;

    }

    @Override
    public void execute() {
        this.deviceDriver.waitAndTypeText(xPath, text);
    }
}
