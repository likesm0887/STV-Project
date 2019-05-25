package entity;

import adapter.CommandMapper;
import adapter.Instruction;
import adapter.device.DeviceDriver;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.exception.AssertException;
import useCase.command.CommandFactory;
import useCase.command.ICommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScriptManager {
    private Config config;
    private TestData testData;
    private DeviceDriver deviceDriver;
    private Map<Path, ScriptRunner> scripts = new HashMap<>();
    private ScriptResult scriptResult = new ScriptResult(new ScriptExecutionTimer());

    public ScriptManager(Config config, DeviceDriver driver) throws Exception {
        this.config = config;
        this.deviceDriver = driver;
        TestDataParser testDataParser = new TestDataParser(config.getTestDataPath());
        testDataParser.parse();
        testData = testDataParser.getTestData();
        List<Path> scriptFiles = getAllFilesPath(config.getScriptPath());
        scriptFiles.forEach(this::loadScript);
    }

    public List<Path> getAllFilesPath(String rootPath) throws IOException {
        return Files.walk(Paths.get(rootPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .sorted()
                .collect(Collectors.toList());
    }

    private void loadScript(Path path) {
        System.out.println(path.toString());
        List<Instruction> instructions = getInstructionsFromScriptFile(path.toString());
        List<ICommand> commands = convertInstructionToCommand(instructions);
        ScriptRunner scriptRunner = createScriptRunner(commands, path.toString());
        scripts.put(path.getFileName(), scriptRunner);
    }

    private List<Instruction> getInstructionsFromScriptFile(String path) {
        try {
            return new ScriptParser().parse(path);
        } catch (Exception e) {
            throw new RuntimeException("Parse script '" + "' failed", e);
        }
    }

    private List<ICommand> convertInstructionToCommand(List<Instruction> instructions) {
        ICommandMapper commandMapper = new CommandMapper(testData, new CommandFactory(deviceDriver));
        return commandMapper.toCommandList(instructions);
    }

    private ScriptRunner createScriptRunner(List<ICommand> commands, String sourcePath) {
        if (config.isTestAnomaly())
            return new AnomalyScriptRunner(commands, sourcePath, deviceDriver);
        return new ScriptRunner(commands, sourcePath);
    }

    public void execute() {
        scripts.forEach((path, scriptRunner) -> {
            try {
                scriptResult.scriptStarted(path.toString());
                performScript(scriptRunner);
                scriptResult.scriptEnded();
            } catch (AssertException e) {
                scriptResult.scriptFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void performScript(ScriptRunner scriptRunner) {
        deviceDriver.restartAppAndCleanData();
        scriptRunner.executeCommands();
    }

    public String summary() {
        return scriptResult.summary();
    }
}
