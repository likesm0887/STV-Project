package adapter.scriptGenerator;

import adapter.Instruction;
import adapter.parser.ScriptParser;
import useCase.command.ICommand;

import java.util.List;

public abstract class AbstractInstructionHandler implements InstructionHandler {


    protected ScriptParser scriptParser = new ScriptParser();
    protected ICommandMapper commandMapper;
    protected List<String> instructions;

    public AbstractInstructionHandler(ICommandMapper commandMapper, List<String> instructions) {
        this.commandMapper = commandMapper;
        this.instructions = instructions;
    }

    @Override
    public void executeInstruction(String instruction) {
        storeInstruction(instruction);
        toCommand(instruction).forEach(ICommand::execute);
    }

    private List<ICommand> toCommand(String instruction) {
        return commandMapper.toCommandList(transformScriptToInstruction(instruction));
    }

    public Instruction transformScriptToInstruction(String instruction) {
        return this.scriptParser.parseLineOfScript(instruction);
    }

    @Override
    public abstract void removeInstruction();

    protected abstract void storeInstruction(String instruction);
}
