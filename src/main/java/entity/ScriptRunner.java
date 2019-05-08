package entity;

import useCase.command.ICommand;

import java.util.List;

public class ScriptRunner {
    private List<ICommand> commands;
    private String sourcePath;

    public ScriptRunner(List<ICommand> commands, String sourcePath) {
        this.commands = commands;
        this.sourcePath = sourcePath;
    }

    public void executeCommands() {
        for (ICommand command : commands)
            command.execute();
    }

    public String getSourceFilePath() {
        return sourcePath;
    }
}

