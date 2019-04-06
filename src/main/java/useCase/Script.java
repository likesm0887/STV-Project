package useCase;

import useCase.command.Command;

import java.util.List;

public class Script {
    private List<Command> commands;
    public Script(List<Command> commands) {
        this.commands = commands;
    }

    public void executeCommands() {
        for (Command command : commands)
            command.execute();
    }

}
