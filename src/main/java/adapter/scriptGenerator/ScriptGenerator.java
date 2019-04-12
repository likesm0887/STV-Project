package adapter.scriptGenerator;

import useCase.command.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScriptGenerator {
    private List<String> instructions = new ArrayList<>();
    private ICommandMapper commandGenerator;


    public ScriptGenerator() {

    }

    public ScriptGenerator(ICommandMapper commandGenerator) {
        this.commandGenerator = commandGenerator;
    }

    public void executeInstruction(String instruction) {
        saveInstruction(instruction);
        Command command = mappingCommandFor(instruction);

        command.execute();
    }

    public Command mappingCommandFor(String instruction) {
        return this.commandGenerator.mappingFrom(instruction);
    }

    public void saveInstruction(String instruction) {
        instructions.add(instruction);
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void removeCurrentInstruction() {
        int lastElementIndex = instructions.size() - 1;
        instructions.remove(lastElementIndex);
    }

    public void writeScriptFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("script.txt"));
            String instructions = formatInstructions();
            writer.write(instructions);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String formatInstructions() {
        String result = "";
        for (int i = 0; i < instructions.size(); i++)
            result += (instructions.get(i) + "\n");
        return result;
    }
}
