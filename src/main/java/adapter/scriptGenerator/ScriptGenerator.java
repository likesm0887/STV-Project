package adapter.scriptGenerator;

import adapter.Instruction;
import adapter.parser.ScriptParser;
import useCase.command.ICommand;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScriptGenerator {
    private List<String> instructions = new ArrayList<>();
    private ScriptParser scriptParser = new ScriptParser();
    private ICommandMapper commandMapper;

    public ScriptGenerator(ICommandMapper commandMapper) {
        this.commandMapper = commandMapper;
    }

    public void executeInstruction(String instruction) {
        saveInstruction(instruction);
        toCommand(instruction).forEach(ICommand::execute);
    }

    public List<ICommand> toCommand(String instruction) {
        return commandMapper.toCommandList(transformScriptToInstruction(instruction));
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

    public Instruction transformScriptToInstruction(String instruction) {
        return this.scriptParser.parseLineOfScript(instruction);
    }
}
