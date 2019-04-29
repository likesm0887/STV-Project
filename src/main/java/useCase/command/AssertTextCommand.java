package useCase.command;

import adapter.device.DeviceDriver;

public class AssertTextCommand extends Command {
    private String text;

    public AssertTextCommand(DeviceDriver deviceDriver, String xPath, String text) {
        super(deviceDriver, xPath);
        this.text = text;
    }

    @Override
    public void execute() {
        deviceDriver.assertText(xPath, text);
    }
}
