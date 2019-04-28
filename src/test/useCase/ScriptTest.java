package useCase;

import adapter.device.DeviceDriver;
import io.appium.java_client.MobileElement;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public class ScriptTest {
    private Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDriver;
    private CommandFactory commandFactory;
    private String xPath = "//*[@class='a']";
    private Script script;

    @Before
    public void setUp() {
        mockDriver = context.mock(DeviceDriver.class);
        commandFactory = new CommandFactory(mockDriver);

        List<Command> commands = new ArrayList<>();
        commands.add(commandFactory.createCommand("TypeText", xPath, ""));
        commands.add(commandFactory.createCommand("Restart", "", ""));
        script = new Script(commands, "");
    }

    @Test
    public void scriptExecute() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).waitAndTypeText(xPath, "");
            oneOf(mockDriver).restartAppAndCleanData();
        }});

        script.executeCommands();
    }
}
