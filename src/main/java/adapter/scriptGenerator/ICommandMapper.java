package adapter.scriptGenerator;

import adapter.Instruction;
import useCase.command.Command;

import java.util.List;

public interface ICommandMapper {
    List<Command> toCommandList(Instruction instruction);
    List<Command> toCommandList(List<Instruction> instructions);
}
