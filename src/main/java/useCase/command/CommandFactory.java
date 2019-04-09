package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.ScreenOrientation;

public class CommandFactory {
    private DeviceDriver deviceDriver;
    public Command commandCreate(String event, String xPath, String parameter) {
        switch (event) {
            case "Click":
                return this.createClickCommand(xPath);
            case "TypeText":
                return this.createTypeTextCommand(xPath, parameter);
            case "Restart":
                return this.createRestartCommand();
            default:
                return this.createClickCommand(xPath);
        }
    }
    public CommandFactory(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    public Command createClickCommand(String xPath) {
        return new ClickCommand(this.deviceDriver, xPath);
    }

    public Command createTypeTextCommand(String xPath, String text) {
        return new TypeTextCommand(this.deviceDriver, xPath, text);
    }

    public Command createRestartCommand() {
        return new RestartCommand(this.deviceDriver);
    }

    public Command createFindElementCommand(String xPath) {
        return new FindElementCommand(this.deviceDriver, xPath);
    }

    public Command createFindElementListCommand(String xPath) {
        return new FindElementListCommand(this.deviceDriver, xPath);
    }

    public Command createLaunchCommand() {
        return new LaunchCommand(this.deviceDriver);
    }

    public Command createRotationCommand(ScreenOrientation screenOrientation) {
        return new RotationCommand(this.deviceDriver, screenOrientation);
    }

    public Command createSwipeElementCommand(String xPath, SwipeElementDirection swipeElementDirection, int offset) {
        return new SwipeElementCommand(this.deviceDriver, xPath, swipeElementDirection, offset);
    }

}
