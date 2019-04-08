package adapter.scriptGenerator;

import useCase.command.Command;

public interface CommandGenerator {
    Command mappingFrom(String instruction);
}
