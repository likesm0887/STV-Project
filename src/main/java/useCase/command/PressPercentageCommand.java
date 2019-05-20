package useCase.command;

import adapter.device.DeviceDriver;

public class PressPercentageCommand extends Command {
    private int percent;

    public PressPercentageCommand(DeviceDriver deviceDriver, String xPath, String percent) {
        super(deviceDriver, xPath);
        this.percent = Integer.parseInt(percent);
    }

    @Override
    public void execute() {
        this.deviceDriver.pressPercentage(xPath, percent);
    }
}
