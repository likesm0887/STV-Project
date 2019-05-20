package useCase;

import adapter.device.DeviceDriver;
import entity.ScriptRunner;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import useCase.command.CommandFactory;
import useCase.command.ICommand;

import java.util.ArrayList;
import java.util.List;

public class ScriptRunnerTest {
    private Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDriver;
    private CommandFactory commandFactory;
    private String xPath = "//*[@class='a']";
    private ScriptRunner scriptRunner;

    @Before
    public void setUp() {
        mockDriver = context.mock(DeviceDriver.class);
        commandFactory = new CommandFactory(mockDriver);

        List<ICommand> commands = new ArrayList<>();
        commands.add(commandFactory.createCommand("TypeText", xPath, ""));
        commands.add(commandFactory.createCommand("Restart", "", ""));
        scriptRunner = new ScriptRunner(commands, "");
    }

    @Test
    public void scriptExecute() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).waitAndTypeText(xPath, "");
            oneOf(mockDriver).restartApp();
        }});

        scriptRunner.executeCommands();
    }
}
