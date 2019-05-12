package adapter.scriptGenerator;

public interface InstructionHandler {
    void executeInstruction(String instruction);
    void removeInstruction();
}
