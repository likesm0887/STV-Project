package entity;

import adapter.device.DeviceDriver;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import useCase.command.CommandFactory;
import useCase.command.ICommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AnomalyScriptRunnerTest {
    private Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDriver;
    private String xPath = "//*[@class='a']";
    private List<ICommand> commands;

    @Before
    public void setUp() {
        mockDriver = context.mock(DeviceDriver.class);
        CommandFactory commandFactory = new CommandFactory(mockDriver);

        commands = new ArrayList<>();
        commands.add(commandFactory.createCommand("TypeText", xPath, ""));
        commands.add(commandFactory.createCommand("Restart", "", ""));
    }

    @Test
    public void scriptExecute() {
        ScriptRunner scriptRunner = new AnomalyScriptRunner(commands, "", mockDriver);
        context.checking(new Expectations(){{
            oneOf(mockDriver).waitAndTypeText(xPath, "");
            exactly(2).of(mockDriver).getActivityName(); will(returnValue("activity"));
            oneOf(mockDriver).pauseApp();
            oneOf(mockDriver).reopenApp();
            exactly(2).of(mockDriver).rotate();
            oneOf(mockDriver).restartApp();
        }});
        scriptRunner.executeCommands();
    }
}