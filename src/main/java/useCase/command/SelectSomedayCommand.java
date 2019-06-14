package useCase.command;

import adapter.device.DeviceDriver;

public class SelectSomedayCommand extends Command {

    public SelectSomedayCommand(DeviceDriver deviceDriver) {
        super(deviceDriver, "");
    }

    @Override
    public void execute() {
        this.deviceDriver.selectSomeday();
    }
}
