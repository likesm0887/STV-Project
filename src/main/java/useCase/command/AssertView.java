package useCase.command;

import adapter.device.DeviceDriver;

public class AssertView extends Command {
    private String exceptView;

    public AssertView(DeviceDriver deviceDriver, String exceptView) {
        super(deviceDriver, "");
        this.exceptView = exceptView;
    }

    @Override
    public void execute() {
        deviceDriver.assertView(exceptView);
    }


}
