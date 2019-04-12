package useCase;

import adapter.device.DeviceDriver;
import io.appium.java_client.MobileElement;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public class ScriptTest {
    Mockery context = new JUnit4Mockery();
    DeviceDriver mockDriver;
    CommandFactory commandFactory;
    String xPath = "//*[@class='a']";
    Script script_1;
    Script script_2;

    @Before
    public void setUp() {
        mockDriver = context.mock(DeviceDriver.class);
        commandFactory = new CommandFactory(mockDriver);

        List<Command> commands_1 = new ArrayList<>();
        commands_1.add(commandFactory.createFindElementCommand(xPath));
        commands_1.add(commandFactory.createTypeTextCommand(xPath, ""));
        commands_1.add(commandFactory.createRestartCommand());
        script_1 = new Script(commands_1, "");

        List<Command> commands_2 = new ArrayList<>();
        commands_2.add(commandFactory.createLaunchCommand());
        commands_2.add(commandFactory.createClickCommand(xPath));
        ScreenOrientation screenOrientation = ScreenOrientation.LANDSCAPE;
        commands_2.add(commandFactory.createRotationCommand(screenOrientation));
        script_2 = new Script(commands_2, "");
    }

    @Test
    public void scriptExecute() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).findElement(xPath);
            will(returnValue(with(any(MobileElement.class))));
            oneOf(mockDriver).waitAndTypeText(xPath, "");
            oneOf(mockDriver).restartAppAndCleanData();
        }});

        script_1.executeCommands();
    }
}
