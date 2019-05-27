package useCase.command;

import adapter.device.DeviceDriver;

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
            case "RestartAndClean":
                return this.createRestartAndCleanCommand();
            case "LaunchApp":
                return this.createLaunchAppCommand();
            case "Rotate":
                return this.createRotateCommand();
            case "Delete":
                return this.createDeleteCommand(xPath, parameter);
            case "Scroll":
                return this.createScrollToElementCommand(xPath, parameter);
            case "ScrollAndClickTimeZone":
                return this.createScrollAndClickTimeZoneCommand(xPath, parameter);
            case "ClickCalenderYear":
                return this.createScrollToCalenderYearAndClickCommand(xPath, parameter);
            case "MoveDown":
                return this.createMoveDownCommand(xPath);
            case "MoveUp":
                return this.createMoveUpCommand(xPath);
            case "AssertExist":
                return this.createAssertExistCommand(xPath);
            case "AssertText":
                return this.createAssertTextCommand(xPath, parameter);
            case "AssertCount":
                return this.createAssertCounts(xPath, parameter);
            case "AssertActivity":
                return this.createAssertActivityCommand(parameter);
            case "PressPercentage":
                return this.createPressPercentageCommand(xPath, parameter);
            case "AssertTextExistCommand":
                return this.createAssertTextExistCommand(parameter);
            case "TypeTextRandomly":
                return this.createTypeTextRandomlyCommand(xPath);
        }

        throw new RuntimeException("Unexpected command type");
    }

    private Command createTypeTextRandomlyCommand(String xPath) {
        return new TypeTextRandomlyCommand(deviceDriver, xPath);
    }

    private Command createAssertTextExistCommand(String parameter) {
        return new AssertTextExistCommand(deviceDriver, parameter);
    }

    private Command createMoveUpCommand(String xPath) {
        return new MoveUpCommand(deviceDriver, xPath);
    }

    private Command createMoveDownCommand(String xPath) {
        return new MoveDownCommand(deviceDriver, xPath);
    }

    private Command createScrollAndClickTimeZoneCommand(String xPath, String parameter) {
        return new ScrollAndClickTimeZoneCommand(deviceDriver, xPath, parameter);
    }

    private Command createScrollToElementCommand(String xPath, String parameter) {
        return new ScrollToElementCommand(deviceDriver, xPath, parameter);
    }

    private Command createScrollToCalenderYearAndClickCommand(String xPath, String parameter) {
        return new ScrollToCalenderYearAndClickCommand(deviceDriver, xPath, parameter);
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

    private Command createPressPercentageCommand(String xPath, String parameter) {
        return new PressPercentageCommand(deviceDriver, xPath, parameter);
    }

    private Command createRestartCommand() {
        return new RestartCommand(deviceDriver);
    }

    private Command createRestartAndCleanCommand() {
        return new RestartAndCleanCommand(deviceDriver);
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

    private Command createAssertExistCommand(String xPath) {
        return new AssertExistCommand(deviceDriver, xPath);
    }

    private Command createAssertTextCommand(String xPath, String text) {
        return new AssertTextCommand(deviceDriver, xPath, text);
    }

    private Command createAssertCounts(String xPath, String count) {
        return new AssertCountCommand(deviceDriver, xPath, count);
    }

    private Command createAssertActivityCommand(String activity) {
        return new AssertActivityCommand(deviceDriver, activity);
    }


}
