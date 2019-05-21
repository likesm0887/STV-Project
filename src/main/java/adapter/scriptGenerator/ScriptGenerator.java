package adapter.scriptGenerator;

import java.util.ArrayList;
import java.util.List;

public class ScriptGenerator {


    public enum ExecuteMode {Single, Batch}

    private InstructionHandler instructionHandler;
    private List<String> instructions = new ArrayList<>();
    private ICommandMapper commandMapper;

    public ScriptGenerator(ICommandMapper commandMapper) {
        this.commandMapper = commandMapper;
        instructionHandler = new SingleInstructionHandler(instructions, commandMapper);
    }

    public void switchMode(ExecuteMode mode) {
        if (mode == ExecuteMode.Single) {
            instructionHandler = new SingleInstructionHandler(instructions, commandMapper);
        } else if (mode == ExecuteMode.Batch) {
            instructionHandler = new BatchInstructionHandler(instructions, commandMapper);
        }
    }

    public void executeInstruction(String instruction) {

        this.instructionHandler.executeInstruction(instruction);
    }

    public void removeInstruction() {
        this.instructionHandler.removeInstruction();
    }

    public int instructionSize() {
        return instructions.size();
    }

    public void writeScriptFile(String fileName) {
        new ScriptFileHandler(instructions).writeScriptFile(fileName);
    }

}
