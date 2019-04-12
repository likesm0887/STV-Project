package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.ScreenOrientation;

import static org.junit.Assert.*;

public class CommandFactoryTest {
    String xPath = "//*[@class='a']";

    final DeviceDriver DUMMY_DRIVER = null;

    @Test
    public void createClickCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command clickCommand = commandFactory.createClickCommand(xPath);

        assertTrue(clickCommand instanceof ClickCommand);
    }

    @Test
    public void createTypeTextCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);
        String text = "fuck up";

        Command typeTextCommand = commandFactory.createTypeTextCommand(xPath, text);

        assertTrue(typeTextCommand instanceof TypeTextCommand);
    }

    @Test
    public void createFindElementCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command findElementCommand = commandFactory.createFindElementCommand(xPath);

        assertTrue(findElementCommand instanceof FindElementCommand);
    }

    @Test
    public void createFindElementListCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command findElementListCommand = commandFactory.createFindElementListCommand(xPath);

        assertTrue(findElementListCommand instanceof FindElementListCommand);
    }

    @Test
    public void createLaunchCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command launchCommand = commandFactory.createLaunchCommand();

        assertTrue(launchCommand instanceof LaunchCommand);
    }

    @Test
    public void createRotationCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command rotationCommand = commandFactory.createRotationCommand(ScreenOrientation.LANDSCAPE);

        assertTrue(rotationCommand instanceof RotationCommand);
    }

    @Test
    public void createSwipeElementCommand() {
        int offset = 10;

        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command swipeElementCommand = commandFactory.createSwipeElementCommand(xPath, SwipeElementDirection.UP, offset);

        assertTrue(swipeElementCommand instanceof SwipeElementCommand);
    }

    @Test
    public void createRestartCommand() {
        CommandFactory commandFactory = new CommandFactory(DUMMY_DRIVER);

        Command restartCommand = commandFactory.createRestartCommand();

        assertTrue(restartCommand instanceof RestartCommand);
    }


}