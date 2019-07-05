package entity;

import org.openqa.selenium.StaleElementReferenceException;
import useCase.command.ICommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScriptRunner {
    private final int MAX_ATTEMPT = 2;
    private List<ICommand> commands;
    private String sourcePath;

    public ScriptRunner(List<ICommand> commands, String sourcePath) {
        this.commands = commands;
        this.sourcePath = sourcePath;
    }

    public void executeCommands() {
        commands.forEach(this::execute);
    }

    private void execute(ICommand command) {
        int attempt = 0;
        boolean retry;
        do {
            try {
                retry = false;
                command.execute();
                afterCommand();
            } catch (StaleElementReferenceException e) {
                attempt++;
                retry = true;
                if (attempt > MAX_ATTEMPT)
                    throw new RuntimeException(e);
            }
        } while (retry);
    }

    protected void afterCommand() {

    }

//
//
//    private void execute1(ICommand command) {
//
//        while (true) {
//            if (isExecutionComplete())
//                break;
//
//
//            try {
//                increaseAttempt();
//                executeCommand(command);
//                executionComplete();
//            } catch (StaleElementReferenceException e) {
//                if (attemptNumberReach())
//                    throw new RuntimeException(e);
//            }
//
//        }
//
//    }
}