package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        Command clickCommand = commandFactory.createClickCommand(xPath);

        assertTrue(clickCommand instanceof ClickCommand);
    }

    @Test
    public void createTypeTextCommand() {
        String text = "fuck up";
        Command typeTextCommand = commandFactory.createTypeTextCommand(xPath, text);

        assertTrue(typeTextCommand instanceof TypeTextCommand);
    }

    @Test
    public void createFindElementCommand() {
        Command findElementCommand = commandFactory.createFindElementCommand(xPath);

        assertTrue(findElementCommand instanceof FindElementCommand);
    }

    @Test
    public void createFindElementListCommand() {
        Command findElementListCommand = commandFactory.createFindElementListCommand(xPath);

        assertTrue(findElementListCommand instanceof FindElementListCommand);
    }

    @Test
    public void createLaunchCommand() {
        Command launchCommand = commandFactory.createLaunchCommand();

        assertTrue(launchCommand instanceof LaunchCommand);
    }

    @Test
    public void createRotationCommand() {
        Command rotationCommand = commandFactory.createRotationCommand(ScreenOrientation.LANDSCAPE);

        assertTrue(rotationCommand instanceof RotationCommand);
    }

    @Test
    public void createSwipeElementCommand() {
        final int offset = 10;
        Command swipeElementCommand = commandFactory.createSwipeElementCommand(xPath, SwipeElementDirection.UP, offset);

        assertTrue(swipeElementCommand instanceof SwipeElementCommand);
    }

    @Test
    public void createRestartCommand() {
        Command restartCommand = commandFactory.createRestartCommand();

        assertTrue(restartCommand instanceof RestartCommand);
    }
}