package adapter;

import adapter.device.DeviceDriver;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.TestData;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import useCase.command.Command;
import useCase.command.CommandFactory;
import useCase.command.ICommand;
import useCase.command.RestartCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JMock.class)
public class CommandMapperTest {
    private final String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private final String SIMPLE_SCRIPT = "./src/test/resources/scriptForMapperTest.txt";
    private TestDataParser parser;
    private TestData testData;
    protected Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
    private ScriptParser scriptParser;
    private List<Instruction> instructions = new ArrayList<>();
    private CommandFactory commandFactory;
    private ICommandMapper commandMapper;

    @Before
    public void setUp() throws Exception {
        parser = new TestDataParser(SIMPLE_TEST_DATA);
        scriptParser = new ScriptParser();
        parser.parse();
        testData = parser.getTestData();
        commandFactory = new CommandFactory(mockDeviceDriver);
        commandMapper = new CommandMapper(testData, commandFactory);
    }

    @Test
    public void mapToSingleInstruction()  {
        Instruction  instruction= scriptParser.parseLineOfScript("View1\tClick\tfolder{list}");
        List<ICommand> commands = commandMapper.toCommandList(instruction);
        assertEquals(1, commands.size());
        assertEquals("//*[@class='android.widget.TextView' and @text='list']", ((Command)commands.get(0)).getXpath());
    }

    @Test
    public void mapScriptToInstructionsList() throws Exception {
        instructions = scriptParser.parse(SIMPLE_SCRIPT);
        List<ICommand> commands = commandMapper.toCommandList(instructions);
        assertEquals(6, commands.size());
        Assert.assertEquals(((Command)commands.get(0)).getXpath(), "//*[@class='android.widget.TextView' and @text='list']");
    }

    @Test
    public void loadAnotherScript() throws Exception {
        final String LOAD_TEST_SCRIPT = "./src/test/resources/loadScript.txt";
        scriptParser = new ScriptParser();
        int commandListSize = commandMapper.toCommandList(scriptParser.parse(LOAD_TEST_SCRIPT)).size();
        Assert.assertEquals(8,commandListSize);
    }

    @Test
    public void mapUnaryInstruction() {
        final String RESTART ="Restart";
        List<ICommand> commands = commandMapper.toCommandList(scriptParser.parseLineOfScript(RESTART));
        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof RestartCommand);
    }
}
