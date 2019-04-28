package useCase.command;

import adapter.device.DeviceDriver;

public class DeleteCommand extends Command {
    private int times;

    public DeleteCommand(DeviceDriver deviceDriver, String xPath, String times) {
        super(deviceDriver, xPath);
        this.times = Integer.parseInt(times);
    }

    @Override
    public void execute() {
        deviceDriver.deleteCharacter(xPath, times);
    }
}
