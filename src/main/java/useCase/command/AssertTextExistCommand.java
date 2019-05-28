package useCase.command;

import adapter.device.DeviceDriver;

public class AssertTextExistCommand extends Command {
    private String text;

    public AssertTextExistCommand(DeviceDriver deviceDriver, String text) {
        super(deviceDriver, "");
        this.text = text;
    }

    @Override
    public void execute() {
        this.deviceDriver.assertTextExist(text);
    }
}
