package useCase;

import adapter.CommandMapper;
import adapter.Instruction;
import adapter.device.DeviceDriver;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.Config;
import entity.Exception.AssertException;
import entity.TestData;
import useCase.command.Command;
import useCase.command.CommandFactory;

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
    private Map<Path, Script> scripts = new HashMap<>();

    private ScriptResult scriptResult = new ScriptResult(new ScriptExecutionTimer());

    public ScriptManager(Config config, DeviceDriver driver) throws Exception {
        this.deviceDriver = driver;
        TestDataParser testDataParser = new TestDataParser(config.getTestDataPath());
        testDataParser.parse();
        testData = testDataParser.getTestData();
        scriptFiles = getAllFilesPath(config.getScriptPath());
//        System.out.println(config.getScriptPath());
        scriptFiles.forEach(path -> transferToScriptObject(path));
    }

    private void transferToScriptObject(Path path) {
        System.out.println(path.toString());
        List<Instruction> instructions = transferScriptFileToInstruction(path.toString());
        List<Command> commands = transferInstructionToCommand(instructions);
        Script script = new Script(commands, path.toString());
        scripts.put(path.getFileName(), script);
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

    private List<Command> transferInstructionToCommand(List<Instruction> instructions) {
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
        for (Map.Entry<Path, Script> entry : scripts.entrySet()) {
            if (entry.getValue().getSourceFilePath().equalsIgnoreCase(scriptPath))
                return true;
        }
        return false;
    }

    public void execute() {

        for (Map.Entry<Path, Script> entry : scripts.entrySet()) {
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

    private void performScript(Script script) {
        deviceDriver.launchApp();
        script.executeCommands();
        deviceDriver.stopApp();
    }

    public String summary() {
        return scriptResult.summary();
    }
}
