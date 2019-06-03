package entity;

import entity.report.ReportGenerator;
import entity.report.ScriptReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class ScriptResult {

    private ExecutionTimer executionTimer;
    private List<ScriptInformation> scriptInformationList = new ArrayList<>();
    private ReportGenerator reportGenerator;

    public ScriptResult(ExecutionTimer executionTimer) {

        this(executionTimer, new ScriptReportGenerator());
    }

    public ScriptResult(ExecutionTimer executionTimer, ReportGenerator reportGenerator) {
        this.executionTimer = executionTimer;
        this.reportGenerator = reportGenerator;
    }

    public void scriptStarted(String scriptName) {
        ScriptInformation scriptInformation = new ScriptInformation(scriptName);

        System.out.println(reportGenerator.generateScriptInfoHeader(scriptInformation));

        scriptInformationList.add(scriptInformation);
        executionTimer.startCounter();
    }

    public void scriptEnded() {
        currentScriptTerminated();
        currentScriptComplete();

        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    private void currentScriptComplete() {
        currentScriptInformation().executionComplete();
    }
    // assertion error
    public void scriptAssertFailed(String errorMessage) {
        scriptFailed(errorMessage);

        System.out.println(reportGenerator.generateScriptInfoBody(currentScriptInformation()));
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }
    // exception error
    public void scriptExceptionFailed(String errorMessage) {
        scriptFailed(errorMessage);

        System.out.println(reportGenerator.generateScriptInfoExceptionBody(currentScriptInformation()));
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    private void scriptFailed(String errorMessage) {
        currentScriptTerminated();
        currentScriptFailed(errorMessage);
    }

    private void currentScriptFailed(String errorMessage) {
        currentScriptInformation().executionFailed(errorMessage);
    }
    private void currentScriptTerminated() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTime());
    }

    private ScriptInformation currentScriptInformation() {
        return this.scriptInformationList.get(lastElementIndex());
    }

    private int lastElementIndex() {
        return this.scriptInformationList.size() - 1;
    }

    public String summary() {
        return reportGenerator.generateScriptSummary(scriptInformationList);

    }

}
