package useCase;

import report.ReportGenerator;
import report.ScriptReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class ScriptResult {

    private ExecutionTimer executionTimer;
    private List<ScriptInformation> scriptInformationList = new ArrayList<>();
    private ReportGenerator reportGenerator = new ScriptReportGenerator();

    public ScriptResult(ExecutionTimer executionTimer) {
        this.executionTimer = executionTimer;
    }

    public void scriptStarted(String scriptName) {
        ScriptInformation scriptInformation = new ScriptInformation(scriptName);
        System.out.println(reportGenerator.generateScriptInfoHeader(scriptInformation));
        scriptInformationList.add(scriptInformation);
        executionTimer.startCounter();
    }


    public void scriptEnded() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTimeInMiniSecond());
        currentScriptInformation().executionComplete();
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    public void scriptFailed() {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTimeInMiniSecond());
        currentScriptInformation().executionFailed();
        System.out.println(reportGenerator.generateScriptInfoBody(currentScriptInformation()));
        System.out.println(reportGenerator.generateScriptInfoFooter(currentScriptInformation()));
    }

    public void scriptFailed(String errorMessage) {
        executionTimer.endCounter();
        currentScriptInformation().setExecutionTime(executionTimer.elapsedTimeInMiniSecond());
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

//    private String informationContents() {
//        String result = this.scriptInformationList.get(0).summary();
//
//        for (int i = 1; i < this.scriptInformationList.size(); i++) {
//            ScriptInformation scriptInformation = this.scriptInformationList.get(i);
//            result += scriptInformation.summary();
//        }
//        return result;
//    }

}
