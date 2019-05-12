package adapter.scriptGenerator;

import adapter.CommandMapper;
import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import adapter.device.DeviceDriver;
import adapter.parser.TestDataParser;
import entity.TestData;
import useCase.command.CommandFactory;

public class ScriptGeneratorFactory {
    public static ScriptGenerator createScriptGenerator() throws Exception {
        ConfigReader configReader = new ConfigReader();
        DeviceDriver driver = new AppiumDriver(configReader.getConfig());
        driver.startService();
        TestDataParser testDataParser = new TestDataParser("./TestData/TestData.xlsx");
        testDataParser.parse();
        TestData testData = testDataParser.getTestData();
        CommandFactory commandFactory = new CommandFactory(driver);
        ICommandMapper commandMapper = new CommandMapper(testData, commandFactory);
        ScriptGenerator scriptGenerator = new ScriptGenerator(commandMapper);
        return scriptGenerator;
    }

}
