package entity;

import adapter.CommandMapper;
import adapter.Instruction;
import adapter.device.DeviceDriver;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.Exception.AssertException;
import useCase.command.CommandFactory;
import useCase.command.ICommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class ScriptManager {
    private TestData testData;
    private DeviceDriver deviceDriver;
    private List<Path> scriptFiles;
    private Map<Path, ScriptRunner> scripts = new HashMap<>();
    private ScriptResult scriptResult = new ScriptResult(new ScriptExecutionTimer());

    public ScriptManager(Config config, DeviceDriver driver) throws Exception {
        this.deviceDriver = driver;
        TestDataParser testDataParser = new TestDataParser(config.getTestDataPath());
        testDataParser.parse();
        testData = testDataParser.getTestData();
        scriptFiles = getAllFilesPath(config.getScriptPath());
        scriptFiles.forEach(path -> transferToScriptObject(path));
    }

    private void transferToScriptObject(Path path) {
        System.out.println(path.toString());
        List<Instruction> instructions = transferScriptFileToInstruction(path.toString());
        List<ICommand> commands = transferInstructionToCommand(instructions);
        ScriptRunner scriptRunner = new ScriptRunner(commands, path.toString());
        scripts.put(path.getFileName(), scriptRunner);
    }

    private List<Instruction> transferScriptFileToInstruction(String path) {
        List<Instruction> instructions = null;
        try {
            instructions = new ScriptParser().parse(path);
        } catch (AssertException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return instructions;
    }

    private List<ICommand> transferInstructionToCommand(List<Instruction> instructions) {
        ICommandMapper commandMapper = new CommandMapper(testData, new CommandFactory(deviceDriver));
        return commandMapper.toCommandList(instructions);
    }

    public List<Path> getAllFilesPath(String rootPath) throws IOException {
        List<Path> allFilePath = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(rootPath))
                                .filter(path -> path.toString().endsWith(".txt"));
        paths.forEach(path -> allFilePath.add(path));
        return allFilePath;
    }

    public boolean isExist(String scriptPath) {
        for (Map.Entry<Path, ScriptRunner> entry : scripts.entrySet()) {
            if (entry.getValue().getSourceFilePath().equalsIgnoreCase(scriptPath))
                return true;
        }
        return false;
    }

    public void execute() {

        for (Map.Entry<Path, ScriptRunner> entry : scripts.entrySet()) {
            try {
                scriptResult.scriptStarted(entry.getKey().toString());
                performScript(entry.getValue());
                scriptResult.scriptEnded();
            } catch (AssertException e) {
                scriptResult.scriptFailed(e.getMessage());
            } catch (Exception e) {
                scriptResult.scriptFailed(e.getMessage());
            }
        }
    }

    private void performScript(ScriptRunner scriptRunner) {
        scriptRunner.executeCommands();
        deviceDriver.restartAppAndCleanData();
    }

    public String summary() {
        return scriptResult.summary();
    }
}
