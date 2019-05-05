package useCase;

import report.ReportGenerator;
import report.ScriptReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class ScriptResult {

    private ExecutionTimer executionTimer;
    private List<ScriptInformation> scriptInformationList = new ArrayList<>();
    private ReportGenerator reportGenerator;
//    = new ScriptReportGenerator();

    public ScriptResult(ExecutionTimer executionTimer) {
//        this.executionTimer = executionTimer;

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
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTime());
        currentScriptInformation().executionComplete();
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    public void scriptFailed() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTime());
        currentScriptInformation().executionFailed();
        System.out.println(reportGenerator.generateScriptInfoBody(currentScriptInformation()));
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    public void scriptFailed(String errorMessage) {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTime());
        currentScriptInformation().executionFailed(errorMessage);
        System.out.println(reportGenerator.generateScriptInfoBody(currentScriptInformation()));
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
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
