package entity;

import useCase.command.ICommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScriptRunner {
    private List<ICommand> commands;
    private String sourcePath;

    public ScriptRunner(List<ICommand> commands, String sourcePath) {
        this.commands = commands;
        this.sourcePath = sourcePath;
    }

    public void executeCommands() {
        commands.forEach(each -> {
            each.execute();
            afterCommand();
        });
    }

    protected void afterCommand() {

    }
}