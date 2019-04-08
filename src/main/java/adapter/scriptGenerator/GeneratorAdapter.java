package adapter.scriptGenerator;

import adapter.parser.CommandBuilder;
import useCase.command.Command;

import java.util.List;

public class GeneratorAdapter implements CommandGenerator {
    private final String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";

    private CommandBuilder commandBuilder;

    public GeneratorAdapter(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public Command mappingFrom(String instruction) {
        try {
            List<Command> commandList = commandBuilder.setScriptPathOrOneCommand(instruction).setTestDataFilePath(SIMPLE_TEST_DATA).build();
            return commandList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
