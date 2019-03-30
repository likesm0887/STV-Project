import adapter.TestData.TestData;
import command.ClickCommand;
import command.Command;
import command.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public class CommandMapper {
    private List<Instruction> instructions;
    private TestData testData;
    private CommandFactory commandFactory;

    public CommandMapper(List<Instruction> instructions, TestData testData,CommandFactory commandFactory) {
        this.testData = testData;
        this.instructions = instructions;
        this.commandFactory=commandFactory;
    }

    public List<Command> mapping() {
        List<Command> commands = new ArrayList<>();
        String xPath="";
        for (Instruction instruction : instructions) {
            if(!instruction.getActivity().equals(""))
            {
                if( instruction.getElementParameter().isPresent()){
                    xPath =testData.getTestDatum(instruction.getActivity(), instruction.getAttribute()).getXPathWithVariable(instruction.getElementParameter().get());
                }
                else
                {
                    xPath =testData.getTestDatum(instruction.getActivity(), instruction.getAttribute()).getXPath();
                }
                System.out.println(xPath);
            }
            commands.add(commandCreate(instruction.getEvent(),xPath,instruction.getEvent()));
        }
        return commands;
    }

    private Command commandCreate(String event ,String xPath,String parameter)
    {
        switch (event){
            case "Click":
                return commandFactory.createClickCommand(xPath);

            case "TypeText":
                return commandFactory.createTypeTextCommand(xPath,parameter);

            case "Restart":
                return commandFactory.createRestartCommand();
            default:
                return commandFactory.createClickCommand(xPath);
        }
    }
}
