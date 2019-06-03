package useCase.command;

import adapter.device.DeviceDriver;

public class WaitForCommand extends Command {
    private int waitingTime;

    public WaitForCommand(DeviceDriver deviceDriver, String waitingTime) {
        super(deviceDriver, "");
        this.waitingTime = Integer.parseInt(waitingTime);
    }

    @Override
    public void execute() {
        this.deviceDriver.waitFor(waitingTime * 1000);
    }
}
