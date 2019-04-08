package adapter.parser;

import adapter.CommandMapper;
import adapter.Instruction;
import adapter.device.DeviceDriver;
import useCase.command.Command;
import useCase.command.CommandFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    private TestDataParser testDataParser;
    private ScriptParser scriptParser;
    private CommandMapper commandMapper;
    private CommandFactory commandFactory;
    private DeviceDriver deviceDriver;
    private String pathOrOneCommand;

    public CommandBuilder(DeviceDriver driver) {
        this.deviceDriver = driver;
    }

    public CommandBuilder setTestDataFilePath(String path) {

        this.testDataParser = new TestDataParser(path);
        return this;
    }

    public CommandBuilder setScriptPathOrOneCommand(String pathOrOneCommand) {

        this.pathOrOneCommand = pathOrOneCommand;
        return this;
    }

    private CommandFactory createCommandFactory() {
        return new CommandFactory(deviceDriver);
    }

    private CommandMapper createCommandMapper(List<Instruction> instructions) {
        return new CommandMapper( testDataParser.getTestData(), createCommandFactory());
    }

    public List<Command> build() throws Exception {
        List<Instruction> instructions = new ArrayList<>();
        this.testDataParser.parse();
        File file = new File(pathOrOneCommand);
        if (file.exists()) {
            this.scriptParser = new ScriptParser(pathOrOneCommand);
            instructions = scriptParser.parse();
        } else {
            this.scriptParser = new ScriptParser();
            instructions.add(scriptParser.parseForOneLine(pathOrOneCommand));
        }
        commandMapper = createCommandMapper(instructions);
        return commandMapper.mappingFrom(scriptParser.parse());
    }
}
