package useCase.command;

import adapter.device.DeviceDriver;

public class SelectCalendarMonthCommand extends Command {
    private int month;
    public SelectCalendarMonthCommand(DeviceDriver driver, String parameter) {
        super(driver, "");
        this.month = Integer.valueOf(parameter);
    }

    @Override
    public void execute() {
        deviceDriver.selectMonth(month);
    }
}
