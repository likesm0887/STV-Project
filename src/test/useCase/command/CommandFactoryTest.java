package useCase.command;

import adapter.device.DeviceDriver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
    public void createRestartAndCleanCommand() {
        Command restartAndCleanCommand = commandFactory.createCommand("RestartAndClean", "", "");

        assertTrue(restartAndCleanCommand instanceof RestartAndCleanCommand);
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

    @Test
    public void createAssertCountCommand() {
        Command assertCountCommand = commandFactory.createCommand("AssertCount","","2");

        assertTrue(assertCountCommand instanceof AssertCountCommand);
    }

    @Test
    public void createAssertActivityCommand() {
        Command assertActivityCommand = commandFactory.createCommand("AssertActivity","","Activity");

        assertTrue(assertActivityCommand instanceof AssertActivityCommand);
    }

    @Test
    public void createAssertTextInCurrentActivity() {
        Command assertTextExistCommand = commandFactory.createCommand("AssertTextExistCommand", "", "myText");
        assertTrue(assertTextExistCommand instanceof AssertTextExistCommand);
    }

    @Test
    public void createTypeTextRandomlyCommand() {
        Command command = commandFactory.createCommand("TypeTextRandomly", "", "");
        assertTrue(command instanceof TypeTextRandomlyCommand);
    }

}