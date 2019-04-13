package useCase;

import adapter.CommandMapper;
import adapter.Instruction;
import adapter.coverage.CodeCovergerator;
import adapter.device.DeviceDriver;
import adapter.parser.ScriptParser;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.Config;
import entity.TestData;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class ScriptManager {
    private TestData testData;
    private DeviceDriver deviceDriver;
    private List<String> scriptFiles;
    private List<Script> scripts = new ArrayList<>();

    public ScriptManager(Config config, DeviceDriver driver) throws Exception {
        this.deviceDriver = driver;
        TestDataParser testDataParser = new TestDataParser(config.getTestDataPath());
        testDataParser.parse();
        testData = testDataParser.getTestData();
        scriptFiles = getAllFilesPath(config.getScriptPath());
        scriptFiles.forEach(path -> transferToScriptObject(path));
    }

    private void transferToScriptObject(String path) {
        List<Instruction> instructions = transferScriptFileToInstruction(path);
        List<Command> commands = transferInstructionToCommand(instructions);
        Script script = new Script(commands, path);
        scripts.add(script);
    }

    private List<Instruction> transferScriptFileToInstruction(String path) {
        List<Instruction> instructions = null;
        try {
            ScriptParser sp = new ScriptParser(path);
            instructions = sp.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instructions;
    }

    private List<Command> transferInstructionToCommand(List<Instruction> instructions) {
        ICommandMapper commandMapper = new CommandMapper(testData, new CommandFactory(deviceDriver));
        return commandMapper.mappingFrom(instructions);
    }

    public List<String> getAllFilesPath(String rootPath) throws IOException {
        List<String> allFilePath = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(rootPath)).filter(Files::isRegularFile);
        paths.forEach(path -> allFilePath.add(path.toString()));
        return allFilePath;
    }

    public boolean isExist(String scriptPath) {
        for (Script script : scripts) {
            if (script.getSourceFilePath().equalsIgnoreCase(scriptPath))
                return true;
        }
        return false;
    }

    public void execute() {
        for (Script script : scripts) {
            deviceDriver.launchApp();
            script.executeCommands();
            deviceDriver.restartApp();
        }
    }
}
