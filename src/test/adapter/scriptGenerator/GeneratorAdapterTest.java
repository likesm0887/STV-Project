package adapter.scriptGenerator;

import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import adapter.device.DeviceDriver;
import adapter.parser.CommandBuilder;
import org.junit.Before;
import org.junit.Test;
import useCase.command.Command;

public class GeneratorAdapterTest {
    protected CommandBuilder commandBuilder;
    protected String instruction = "View1\tClick{}\tfolder_list{0}";
    @Before
    public void setUp() {
        ConfigReader configReader = new ConfigReader();
        DeviceDriver driver = new AppiumDriver(configReader.getConfig());
        driver.startAppiumService();
        commandBuilder = new CommandBuilder(driver);
    }

    @Test
    public void Test() throws Exception {
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(commandBuilder);
        Command command = generatorAdapter.mappingFrom(instruction);
        command.execute();
    }

}