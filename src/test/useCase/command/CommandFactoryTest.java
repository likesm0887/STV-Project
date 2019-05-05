package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;

import static org.junit.Assert.*;

public class CommandFactoryTest {
    private String xPath = "//*[@class='a']";
    private final DeviceDriver DUMMY_DRIVER = null;
    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        commandFactory = new CommandFactory(DUMMY_DRIVER);
    }

    @Test
    public void createClickCommand() {
        Command clickCommand = commandFactory.createCommand("Click", xPath, "");

        assertTrue(clickCommand instanceof ClickCommand);
    }

    @Test
    public void createTypeTextCommand() {
        String text = "some text";
        Command typeTextCommand = commandFactory.createCommand("TypeText", xPath, text);

        assertTrue(typeTextCommand instanceof TypeTextCommand);
    }

    @Test
    public void createLaunchAppCommand() {
        Command launchCommand = commandFactory.createCommand("LaunchApp", "", "");

        assertTrue(launchCommand instanceof LaunchAppCommand);
    }

    @Test
    public void createRotationCommand() {
        Command rotationCommand = commandFactory.createCommand("Rotate", "", "");

        assertTrue(rotationCommand instanceof RotateCommand);
    }

    @Test
    public void createSwipeElementCommand() {
        final int offset = 10;
        Command swipeElementCommand = commandFactory.createSwipeElementCommand(xPath, SwipeElementDirection.UP, offset);

        assertTrue(swipeElementCommand instanceof SwipeElementCommand);
    }

    @Test
    public void createDeleteCommand() {
        final String times = "3";
        Command deleteCommand = commandFactory.createCommand("Delete", xPath, times);

        assertTrue(deleteCommand instanceof DeleteCommand);
    }

    @Test
    public void createRestartCommand() {
        Command restartCommand = commandFactory.createCommand("Restart", "", "");

        assertTrue(restartCommand instanceof RestartCommand);
    }

    @Test
    public void createAssertExistCommand() {
        Command assertExistCommand = commandFactory.createCommand("AssertExist", xPath, "");

        assertTrue(assertExistCommand instanceof AssertExistCommand);
    }

    @Test
    public void createAssertTextCommand() {
        Command assertTextCommand = commandFactory.createCommand("AssertText", xPath, "text");

        assertTrue(assertTextCommand instanceof AssertTextCommand);
    }
}