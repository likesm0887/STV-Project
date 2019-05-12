package adapter.scriptGenerator;

import adapter.Instruction;
import adapter.parser.ScriptParser;
import useCase.command.ICommand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BatchInstructionHandler extends AbstractInstructionHandler {

    private List<String> batchInstructions = new ArrayList<>();

    public BatchInstructionHandler(List<String> instructions, ICommandMapper commandMapper) {
        super(commandMapper, instructions);
    }

    public List<String> getInstructions() {
        // defensive copy
        List<String> copy = instructions.stream().collect(Collectors.toList());
        return copy;
    }


    protected void storeInstruction(String instruction) {
        this.instructions.add(instruction);
        this.batchInstructions.add(instruction);
    }

    @Override
    public void removeInstruction() {
        int batchSize = batchInstructions.size();
        int allInstructionSize = instructions.size();
        instructions.subList(allInstructionSize - batchSize, allInstructionSize).clear();
    }

}
