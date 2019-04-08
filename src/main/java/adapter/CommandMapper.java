package adapter;

import adapter.parser.ScriptParser;
import adapter.scriptGenerator.CommandGenerator;
import entity.TestData;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public class CommandMapper implements CommandGenerator {
    private TestData testData;
    private CommandFactory commandFactory;

    public CommandMapper(TestData testData, CommandFactory commandFactory) {
        this.testData = testData;
        this.commandFactory = commandFactory;
    }

    private Command mapping(Instruction instruction) {
        String xPath;
        if (!instruction.getActivity().equals("")) {
            if (instruction.getElementParameter().isPresent()) {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getAttribute()).getXPathWithVariable(instruction.getElementParameter().get());
            } else {
                xPath = testData.getTestDatum(instruction.getActivity(), instruction.getAttribute()).getXPath();
            }

            return commandCreate(instruction.getEvent(), xPath, instruction.getEventParameter().orElse(""));
        }
        return null;
    }

    private List<Instruction> inputScriptPathToGetInstruction(String path) throws Exception {
        ScriptParser scriptParser = new ScriptParser(path);
        return scriptParser.parse();
    }

    private List<Command> mapping(List<Instruction> instructions) throws Exception {
        List<Command> commands = new ArrayList<>();
        for (Instruction instruction : instructions) {
            if (instruction.getEvent().equals("LoadScript")) {
                List<Instruction> instructionsTemp = inputScriptPathToGetInstruction(instruction.getEventParameter().get());
                commands.addAll(this.mapping(instructionsTemp));
                continue;
            }
            commands.add(this.mapping(instruction));
        }
        return commands;
    }

    private Command commandCreate(String event, String xPath, String parameter) {
        switch (event) {
            case "Click":
                return commandFactory.createClickCommand(xPath);
            case "TypeText":
                return commandFactory.createTypeTextCommand(xPath, parameter);
            case "Restart":
                return commandFactory.createRestartCommand();
            default:
                return commandFactory.createClickCommand(xPath);
        }
    }

    @Override
    @Deprecated
    public Command mappingFrom(String instruction) {
        return null;
    }

    @Override
    public Command mappingFrom(Instruction instruction) {
        return this.mapping(instruction);
    }

    @Override
    public List<Command> mappingFrom(List<Instruction> instructions) {
        try {
            return this.mapping(instructions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
