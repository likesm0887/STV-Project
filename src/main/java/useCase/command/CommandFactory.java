package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;

public class CommandFactory {
    private DeviceDriver deviceDriver;
    public Command createCommand(String event, String xPath, String parameter) {
        switch (event) {
            case "Click":
                return this.createClickCommand(xPath);
            case "TypeText":
                return this.createTypeTextCommand(xPath, parameter);
            case "Restart":
                return this.createRestartCommand();
            case "LaunchApp":
                return this.createLaunchAppCommand();
            case "Rotate":
                return this.createRotateCommand();
            case "Delete":
                return this.createDeleteCommand(xPath, parameter);
        }
        throw new RuntimeException("Unexpected command type");
    }

    public CommandFactory(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    private Command createClickCommand(String xPath) {
        return new ClickCommand(deviceDriver, xPath);
    }

    private Command createTypeTextCommand(String xPath, String text) {
        return new TypeTextCommand(deviceDriver, xPath, text);
    }

    private Command createRestartCommand() {
        return new RestartCommand(deviceDriver);
    }

    private Command createLaunchAppCommand() {
        return new LaunchAppCommand(deviceDriver);
    }

    private Command createRotateCommand() {
        return new RotateCommand(deviceDriver);
    }

    private Command createDeleteCommand(String xPath, String times) {
        return new DeleteCommand(deviceDriver, xPath, times);
    }

    // TODO: should redefine interface
    private Command createFindElementCommand(String xPath) {
        return new FindElementCommand(deviceDriver, xPath);
    }

    private Command createFindElementListCommand(String xPath) {
        return new FindElementListCommand(deviceDriver, xPath);
    }

    public Command createSwipeElementCommand(String xPath, SwipeElementDirection swipeElementDirection, int offset) {
        return new SwipeElementCommand(deviceDriver, xPath, swipeElementDirection, offset);
    }
}
