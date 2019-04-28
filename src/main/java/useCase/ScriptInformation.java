package useCase;

public class ScriptInformation {

    private String taskName;

    private boolean executionResult;

    private double executionTime;

    public ScriptInformation(String taskName) {
        this.taskName = taskName;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    private String resultStatus() {
        if (this.executionResult)
            return "PASS";
        else
            return "FAILED";
    }

    public String summary() {
        String leftAlignFormat = "| %-15s | %-15s | %-15s |%n";
        return String.format(leftAlignFormat, taskName, executionTime + "ms", resultStatus());
    }

    public void executionComplete() {
        this.executionResult = true;
    }

    public void executionFailed() {
        this.executionResult = false;
    }
}
