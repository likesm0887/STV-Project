package useCase.command;

import adapter.device.DeviceDriver;

public class SelectTomorrowCommand extends Command {

    public SelectTomorrowCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.selectTomorrow();
    }
}
