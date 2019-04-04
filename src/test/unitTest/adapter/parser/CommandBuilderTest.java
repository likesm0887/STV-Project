package adapter.parser;

import adapter.CommandMapper;
import adapter.device.DeviceDriver;
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
    private String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private String SIMPLE_SCRIPT = "./src/test/resources/scriptForMapperTest.txt";
    protected Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);

    @Test
    public void test2() throws Exception {
        CommandBuilder commandBuilder = new CommandBuilder(mockDeviceDriver);
        List<Command> commands = commandBuilder.setScriptPathOrOneCommand(SIMPLE_SCRIPT).setTestDataFilePath(SIMPLE_TEST_DATA).build();
        List<Command> commandsForOneInstruction = commandBuilder.setScriptPathOrOneCommand("View1\tClick{}\tfolder_list{0}").setTestDataFilePath(SIMPLE_TEST_DATA).build();
    }

}