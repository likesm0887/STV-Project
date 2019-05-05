package useCase.command;

public class NullCommand extends Command {
    public NullCommand() {
        super(null, "");
    }

    @Override
    public void execute() {
        // do nothing
    }
}
