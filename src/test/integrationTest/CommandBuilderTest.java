import adapter.CommandMapper;
import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import adapter.device.DeviceDriver;
import adapter.parser.CommandBuilder;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.List;

@RunWith(JMock.class)
public class CommandBuilderTest {
    private String SIMPLE_TEST_DATA = "./testData/test data.xlsx";
    private String SIMPLE_SCRIPT = "./script/quickAddTask.txt";
    protected Mockery context = new JUnit4Mockery();
    //private DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
    ConfigReader configReader = new ConfigReader();
    private DeviceDriver deviceDriver = new AppiumDriver(configReader.getConfig());
    @Test
    public void test2() throws Exception {
        CommandBuilder commandBuilder = new CommandBuilder(deviceDriver);
        List<Command> commands = commandBuilder.setScriptPathOrOneCommand(SIMPLE_SCRIPT).setTestDataFilePath(SIMPLE_TEST_DATA).build();
        //List<Command> commandsForOneInstruction = commandBuilder.setScriptPathOrOneCommand("home\tClick\tfloat_add_btn").setTestDataFilePath(SIMPLE_TEST_DATA).build();
        for (Command command :commands)
        {
            command.execute();
        }
    }

}