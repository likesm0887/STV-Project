package useCase.command;

import adapter.device.DeviceDriver;

public class AssertActivityCommand extends Command {
    private String exceptActivity;

    public AssertActivityCommand(DeviceDriver deviceDriver, String exceptActivity) {
        super(deviceDriver, "");
        this.exceptActivity = exceptActivity;
    }

    @Override
    public void execute() {
        deviceDriver.assertActivity(exceptActivity);
    }


}
