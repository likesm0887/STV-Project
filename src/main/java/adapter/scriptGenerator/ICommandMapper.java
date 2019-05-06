package adapter.scriptGenerator;

import adapter.Instruction;
import useCase.command.ICommand;

import java.util.List;

public interface ICommandMapper {
    List<ICommand> toCommandList(Instruction instruction);
    List<ICommand> toCommandList(List<Instruction> instructions);
}
