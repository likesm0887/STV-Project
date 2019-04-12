package adapter;

import adapter.device.DeviceDriver;
import adapter.scriptGenerator.ICommandMapper;
import entity.TestData;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import org.junit.BeforeClass;
import useCase.command.Command;
import useCase.command.CommandFactory;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ICommandMapper commandGenerator;

    @Before
    public void setUp() throws Exception {
        parser = new TestDataParser(SIMPLE_TEST_DATA);
        scriptParser = new ScriptParser(SIMPLE_SCRIPT);
        parser.parse();
        testData = parser.getTestData();
        commandFactory = new CommandFactory(mockDeviceDriver);
        commandGenerator = new CommandMapper(testData, commandFactory);
    }

    @Test
    public void mappingFromTestForInstructionsList() throws Exception {

        instructions = scriptParser.parse();
        List<Command> commands = commandGenerator.mappingFrom(instructions);
        Assert.assertEquals(commands.get(0).getXpath(), "//*[@class='android.widget.TextView' and @text='list']");
    }

    @Test
    public void mappingFromTestForInstruction()  {
        Instruction  instruction= scriptParser.parseForOneLine("View1\tClick\tfolder{list}");
        Command command = commandGenerator.mappingFrom(instruction);
        Assert.assertEquals(command.getXpath(), "//*[@class='android.widget.TextView' and @text='list']");
    }
    @Test
    public void mappingFromLoadScript() throws Exception {
        final String LOAD_TEST_SCRIPT = "./src/test/resources/loadScript.txt";
        scriptParser = new ScriptParser(LOAD_TEST_SCRIPT);
        int commandListSize = commandGenerator.mappingFrom(scriptParser.parse()).size();
        Assert.assertEquals(8,commandListSize);
    }
    @Test
    public void test() throws Exception {
        final String Restart ="Restart";
        Command restart = commandGenerator.mappingFrom(scriptParser.parseForOneLine(Restart));

    }
}
