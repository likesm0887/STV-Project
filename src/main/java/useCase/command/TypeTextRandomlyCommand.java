package useCase.command;

import adapter.device.DeviceDriver;
import org.apache.commons.lang3.RandomStringUtils;

public class TypeTextRandomlyCommand extends Command {


    public TypeTextRandomlyCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        String randomText = generateRandomText();
        this.deviceDriver.waitAndTypeText(xPath, randomText);
    }

    public String generateRandomText() {
        String randomText = RandomStringUtils.randomAlphanumeric(10);
        return randomText;
    }

}
