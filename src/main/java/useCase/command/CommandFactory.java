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
            case "SelectTimeZone":
                return this.createSelectTimeZoneCommand(xPath, parameter);
            case "SelectCalenderYear":
                return this.createSelectCalenderYearCommand(xPath, parameter);
            case "MoveDown":
                return this.createMoveDownCommand(xPath);
            case "MoveUp":
                return this.createMoveUpCommand(xPath);
            case "SelectTomorrow":
                return this.createSelectTomorrow();
            case "SelectSomeday":
                return this.createSelectSomeday();
            case "PressPercentage":
                return this.createPressPercentageCommand(xPath, parameter);
            case "Swipe":
                return this.createSwipeElementCommand(xPath, parameter);
            case "AssertExist":
                return this.createAssertExistCommand(xPath);
            case "AssertNotExist":
                return this.createAssertNotExistCommand(xPath);
            case "AssertText":
                return this.createAssertTextCommand(xPath, parameter);
            case "AssertCount":
                return this.createAssertCounts(xPath, parameter);
            case "AssertView":
                return this.createAssertActivityCommand(parameter);
            case "AssertTextExist":
                return this.createAssertTextExistCommand(parameter);
            case "TypeTextRandomly":
                return this.createTypeTextRandomlyCommand(xPath);
            case "PressBackKey":
                return this.createPressBackKeyCommand();
            case "PressEnter":
                return this.createPressEnterCommand();
            case "WaitFor":
                return this.createWaitForCommand(parameter);
        }

        throw new RuntimeException("Unexpected command type: " + event);
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

    private Command createSelectTimeZoneCommand(String xPath, String parameter) {
        return new SelectTimeZoneCommand(deviceDriver, xPath, parameter);
    }

    private Command createScrollToElementCommand(String xPath, String parameter) {
        return new ScrollToElementCommand(deviceDriver, xPath, parameter);
    }

    private Command createSelectCalenderYearCommand(String xPath, String parameter) {
        return new SelectCalenderYearCommand(deviceDriver, xPath, parameter);
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

    private Command createSelectTomorrow() {
        return new SelectTomorrowCommand(deviceDriver);
    }

    private Command createSelectSomeday() {
        return new SelectSomedayCommand(deviceDriver);
    }

    private Command createAssertExistCommand(String xPath) {
        return new AssertExistCommand(deviceDriver, xPath);
    }

    private Command createAssertNotExistCommand(String xPath) {
        return new AssertNotExistCommand(deviceDriver, xPath);
    }

    private Command createAssertTextCommand(String xPath, String text) {
        return new AssertTextCommand(deviceDriver, xPath, text);
    }

    private Command createAssertCounts(String xPath, String count) {
        return new AssertCountCommand(deviceDriver, xPath, count);
    }

    private Command createAssertActivityCommand(String activity) {
        return new AssertView(deviceDriver, activity);
    }

    private Command createPressBackKeyCommand() {
        return new PressBackKeyCommand(deviceDriver);
    }

    private Command createPressEnterCommand()
    {
        return new PressEnterCommand(deviceDriver);
    }

    private Command createWaitForCommand(String waitingTime) {
        return new WaitForCommand(deviceDriver, waitingTime);
    }

    private Command createSwipeElementCommand(String xPath, String direction) {
        return new SwipeElementCommand(deviceDriver, xPath, direction);
    }
}
