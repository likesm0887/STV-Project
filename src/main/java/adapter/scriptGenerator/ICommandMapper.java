package adapter.scriptGenerator;

import useCase.command.Command;

public interface ICommandMapper {
    Command mappingFrom(String instruction);
}
