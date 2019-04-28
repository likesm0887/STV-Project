package useCase;

import java.util.ArrayList;
import java.util.List;

public class ScriptResult {

    private ExecutionTimer executionTimer;
    private List<ScriptInformation> scriptInformationList = new ArrayList<>();

    public ScriptResult(ExecutionTimer executionTimer) {
        this.executionTimer = executionTimer;
    }

    public void scriptStarted(String taskName) {

        scriptInformationList.add(new ScriptInformation(taskName));
        executionTimer.startCounter();
    }


    public void scriptEnded() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTimeInMiniSecond());
        currentScriptInformation().executionComplete();
    }

    private ScriptInformation currentScriptInformation() {
        return this.scriptInformationList.get(lastElementIndex());
    }

    private int lastElementIndex() {
        return this.scriptInformationList.size() - 1;
    }

    public String summary() {

        String column = "+-----------------+-----------------+-----------------+\n" +
                        "| TaskName        | Times           | State           |\n" +
                        "+-----------------+-----------------+-----------------+\n";

        String body = informationContents();

        String footer = "+-----------------+-----------------+-----------------+\n";

        return column + body + footer;

    }

    private String informationContents() {
        String result = this.scriptInformationList.get(0).summary();

        for (int i = 1; i < this.scriptInformationList.size(); i++) {
            ScriptInformation scriptInformation = this.scriptInformationList.get(i);
            result += scriptInformation.summary();
        }
        return result;
    }

    public void scriptFailed() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTimeInMiniSecond());
        currentScriptInformation().executionFailed();
    }
}
