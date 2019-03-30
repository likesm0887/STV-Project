import adapter.DeviceDriver;
import adapter.TestData.TestData;
import adapter.TestData.TestDataParser;
import command.Command;
import command.CommandFactory;
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
    private String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private String SIMPLE_SCRIPT = "./src/test/resources/scriptForMapperTest.txt";
    private TestDataParser parser;
    private TestData testData;
    protected Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
    private ScriptParser scriptParser;
    private List<Instruction> instructions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        parser = new TestDataParser(SIMPLE_TEST_DATA);
        scriptParser = new ScriptParser(SIMPLE_SCRIPT);
        parser.parse();
        testData = parser.getTestData();
        instructions = scriptParser.parse();
    }

    @Test
    public void test2() throws IOException {
        CommandFactory commandFactory = new CommandFactory(mockDeviceDriver);
        CommandMapper commandMapper = new CommandMapper(instructions, testData, commandFactory);
        List<Command> commands = commandMapper.mapping();
        Assert.assertEquals(commands.get(0).getXpath(), "//*[@class='android.widget.RelativeLayout' and @index='0']");

    }
}
