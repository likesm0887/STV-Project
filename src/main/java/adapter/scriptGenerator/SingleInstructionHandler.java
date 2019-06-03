package adapter.scriptGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class SingleInstructionHandler extends AbstractInstructionHandler {

    public SingleInstructionHandler(List<String> instructions, ICommandMapper commandMapper) {
        super(commandMapper, instructions);

    }

    public List<String> getInstructions() {
        // defensive copy
        List<String> copy = instructions.stream().collect(Collectors.toList());
        return copy;
    }

    protected void storeInstruction(String instruction) {
        this.instructions.add(instruction);
    }

    @Override
    public void removeInstruction() {
        int lastElementIndex = instructions.size() - 1;
        instructions.remove(lastElementIndex);
    }

}
