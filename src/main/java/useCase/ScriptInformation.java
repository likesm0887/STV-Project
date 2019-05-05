package useCase;

public class ScriptInformation {

    private String scriptName = "";

    private boolean executionResult = false;

    private double executionTime = 0.0;

    private String errorMessage = "";

    public ScriptInformation(String scriptName) {
        this.scriptName = scriptName;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public String getScriptName() {
        return scriptName;
    }

    public String resultStatus() {
        if (this.executionResult)
            return  "PASS" ;
        else
            return  "FAILED";
    }

    public String summary() {
        String leftAlignFormat = "| %-15s | %-15s | %-15s |%n";
        return String.format(leftAlignFormat, scriptName, executionTime + "ms", resultStatus());
    }

    public void executionComplete() {
        this.executionResult = true;
    }

    public void executionFailed() {
        this.executionResult = false;
    }

    public void executionFailed(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
