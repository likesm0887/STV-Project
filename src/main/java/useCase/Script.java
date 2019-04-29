package useCase;

import entity.Exception.AssertException;
import useCase.command.Command;

import java.util.List;

public class Script {
    private List<Command> commands;
    private String sourcePath;

    public Script(List<Command> commands, String sourcePath) {
        this.commands = commands;
        this.sourcePath = sourcePath;
    }

    public void executeCommands() {
        for (Command command : commands)
            command.execute();
    }

    public String getSourceFilePath() {
        return sourcePath;
    }
}
