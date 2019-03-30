package command;

import adapter.DeviceDriver;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.ScreenOrientation;

import static org.junit.Assert.*;

@RunWith(JMock.class)
public class CommandFactoryTest {
    Mockery context = new JUnit4Mockery();
    String xPath = "//*[@class='a']";

    @Test
    public void createClickCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command clickCommand = commandFactory.createClickCommand(xPath);

        assertTrue(clickCommand instanceof ClickCommand);
    }

    @Test
    public void createTypeTextCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        String text = "fuck up";

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command typeTextCommand = commandFactory.createTypeTextCommand(xPath, text);

        assertTrue(typeTextCommand instanceof TypeTextCommand);
    }

    @Test
    public void createFindElementCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command findElementCommand = commandFactory.createFindElementCommand(xPath);

        assertTrue(findElementCommand instanceof FindElementCommand);
    }

    @Test
    public void createFindElementListCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command findElementListCommand = commandFactory.createFindElementListCommand(xPath);

        assertTrue(findElementListCommand instanceof FindElementListCommand);
    }

    @Test
    public void createLaunchCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command launchCommand = commandFactory.createLaunchCommand();

        assertTrue(launchCommand instanceof LaunchCommand);
    }

    @Test
    public void createRotationCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command rotationCommand = commandFactory.createRotationCommand(ScreenOrientation.LANDSCAPE);

        assertTrue(rotationCommand instanceof RotationCommand);
    }

    @Test
    public void createSwipeElementCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        int offset = 10;

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command swipeElementCommand = commandFactory.createSwipeElementCommand(xPath, SwipeElementDirection.UP, offset);

        assertTrue(swipeElementCommand instanceof SwipeElementCommand);
    }

    @Test
    public void createRestartCommand() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command restartCommand = commandFactory.createRestartCommand();

        assertTrue(restartCommand instanceof RestartCommand);
    }


}