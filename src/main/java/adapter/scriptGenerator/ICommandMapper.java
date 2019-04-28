package adapter.scriptGenerator;

import adapter.Instruction;
import useCase.command.Command;

import java.util.List;

public interface ICommandMapper {
<<<<<<< HEAD
=======


>>>>>>> 5f75eac3e53e2e40de234e5e601ce183053792c8
    Command mappingFrom(Instruction instruction);

    List<Command> mappingFrom(List<Instruction> instructions);
}
