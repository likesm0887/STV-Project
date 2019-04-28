package adapter;

import adapter.parser.ScriptParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.TestData;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public class CommandMapper implements ICommandMapper {
    private TestData testData;
    private CommandFactory commandFactory;

    public CommandMapper(TestData testData, CommandFactory commandFactory) {
        this.testData = testData;
        this.commandFactory = commandFactory;
    }

    @Override
    public List<Command> toCommandList(Instruction instruction) {
        return map(instruction);
    }

    @Override
    public List<Command> toCommandList(List<Instruction> instructions) {
        List<Command> commands = new ArrayList<>();
        instructions.forEach(each -> commands.addAll(map(each)));
        return commands;
    }

    private List<Command> map(Instruction instruction) {
        List<Command> commands = new ArrayList<>();
        if (instruction.getEvent().equals("LoadScript"))
            appendTargetScript(commands, instruction.getEventParameter().get());
        else
            commands.add(mapToSingleCommand(instruction));
        return commands;
    }

    private void appendTargetScript(List<Command> commands, String targetScript) {
        List<Instruction> instructions = loadTargetScriptInstructions(targetScript);
        commands.addAll(toCommandList(instructions));
    }

    private List<Instruction> loadTargetScriptInstructions(String targetScript) {
        try {
            return new ScriptParser().parse(targetScript);
        } catch (Exception e) {
            throw new RuntimeException("Load script " + targetScript + " fail");
        }
    }

    private Command mapToSingleCommand(Instruction instruction) {
        String xPath = "";
        if (!instruction.getActivity().equals("")) {
            if (instruction.getElementParameter().isPresent()) {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getElement()).getXPathWithVariable(instruction.getElementParameter().get());
            } else {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getElement()).getXPath();
            }
        }
        return commandFactory.commandCreate(instruction.getEvent(), xPath, instruction.getEventParameter().orElse(""));
    }
}
