package adapter;

import adapter.parser.ScriptParser;
import adapter.scriptGenerator.ICommandMapper;
import entity.TestData;
import useCase.command.CommandFactory;
import useCase.command.ICommand;

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
    public List<ICommand> toCommandList(Instruction instruction) {
        return map(instruction);
    }

    @Override
    public List<ICommand> toCommandList(List<Instruction> instructions) {
        List<ICommand> commands = new ArrayList<>();
        instructions.forEach(each -> commands.addAll(map(each)));
        return commands;
    }

    private List<ICommand> map(Instruction instruction) {
        List<ICommand> commands = new ArrayList<>();
        if (instruction.getEvent().equals("LoadScript"))
            appendTargetScript(commands, instruction.getEventParameter().get());
        else
            commands.add(mapToSingleCommand(instruction));
        return commands;
    }

    private void appendTargetScript(List<ICommand> commands, String targetScript) {
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

    private ICommand mapToSingleCommand(Instruction instruction) {
        String xPath = "";
        if (!instruction.getActivity().equals("")) {
            if (instruction.getElementParameter().isPresent()) {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getElement()).getXPathWithVariable(instruction.getElementParameter().get());
            } else {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getElement()).getXPath();
            }
        }

        return commandFactory.createCommand(instruction.getEvent(), xPath, instruction.getEventParameter().orElse(""));
    }
}
