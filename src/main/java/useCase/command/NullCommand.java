package useCase.command;

public class NullCommand extends Command {
    public NullCommand() {
        super(null, "");
    }

    @Override
    public void execute() {
        System.out.println("Execute Null Command, which will not cause anything side effect");
    }
}
