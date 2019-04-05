package scriptGenerator;

import useCase.command.Command;

public interface CommandGenerator {
    Command mappingCommandFor(String instruction);
}
