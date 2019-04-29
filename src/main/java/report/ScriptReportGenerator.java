package report;

import useCase.ScriptInformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScriptReportGenerator implements ReportGenerator {
    private final String headerPrefix = "> Starting: ";
    private final String spendingTimePrefix = "> Spending Time: ";
    private final String statusPrefix = "> Status: ";
    private final String assertErrorPrefix = "> Assert Error: ";
    private final String totalTimePrefix = "Total: ";

    private ColorHelper colorHelper = new ColorHelper();

    @Override
    public String generateScriptInfoHeader(ScriptInformation scriptInformation) {
        String header = "+-----------------------------------------------------+\n";
        String scriptName = scriptInformation.getScriptName() + "\n";
        return header + headerPrefix + scriptName;
    }

    @Override
    public String generateScriptInfoFooter(ScriptInformation scriptInformation) {
        String spendingTime = transformToSecond(scriptInformation.getExecutionTime()) + "s\n";
        String status = scriptInformation.resultStatus() + "\n";
        return spendingTimePrefix + spendingTime + statusPrefix + decorateStatus(status);
    }

    private String decorateStatus(String status) {
        if (status.contains("PASS") || status.contains("pass"))
            return colorHelper.paintingGreen(status);
        return colorHelper.paintingRed(status);
    }

    @Override
    public String generateScriptInfoBody(ScriptInformation scriptInformation) {
        String errorMessage = scriptInformation.getErrorMessage() + "\n";
        return assertErrorPrefix + errorMessage;
    }

    @Override
    public String generateScriptSummary(List<ScriptInformation> scriptInformationList) {
        String header = generatorHeader();
        String body = generateBody(scriptInformationList);
        String footer = generateFooter(scriptInformationList);

        return header + body + footer;
    }

    private String generateFooter(List<ScriptInformation> scriptInformationList) {
        String header = "+--------------------------------------------------------------------+-----------------+-----------------+\n";
        double totalTime = scriptInformationList.stream().mapToDouble(ScriptInformation::getExecutionTime).sum();
        int pass = (int) scriptInformationList.stream().filter(scriptInformation -> scriptInformation.resultStatus().equalsIgnoreCase("PASS")).count();
        int fail = (int) scriptInformationList.stream().filter(scriptInformation -> scriptInformation.resultStatus().equalsIgnoreCase("FAILED")).count();
        String footer = "+--------------------------------------------------------------------------------------------------------+\n";

        String totalTimeSummary = totalTimePrefix + timeFormat(totalTime / 60) + " min";
        String statusSummary = pass + " " + colorHelper.paintingGreen("passed") + ", " + fail + " " + colorHelper.paintingRed("failed");
        String leftAlignFormat = "| %-76s | %-41s |%n";
        return header + String.format(leftAlignFormat, totalTimeSummary, statusSummary) + footer;
    }

    private double timeFormat(double totalTime) {
        DecimalFormat newFormat = new DecimalFormat("#.##");
        return Double.valueOf(newFormat.format(totalTime));
    }

    private String generateBody(List<ScriptInformation> scriptInformationList) {
        List<String> scriptBodyList = getScriptBodyList(scriptInformationList);
        String result = "";
        for (String s : scriptBodyList)
            result += s;
        return result;
    }

    private String generatorHeader() {
        String header = "+--------------------------------------------------------------------+-----------------+-----------------+\n" +
                        "| ScriptName                                                         | Times           | Status          |\n" +
                        "+--------------------------------------------------------------------+-----------------+-----------------+\n";
        return header;
    }

    private String transformToSecond(double executionTime) {
        return String.valueOf(executionTime / 1000);
    }

    public List<String> getScriptBodyList(List<ScriptInformation> scriptInformations) {
        List<String> result = new ArrayList<>();
        String leftAlignFormat = "| %-66s | %-15s | %-24s |%n";

        for(ScriptInformation s : scriptInformations)
            result.add(String.format(leftAlignFormat, s.getScriptName(), s.getExecutionTime() + " s", decorateStatus(s.resultStatus())));
        return result;
    }



}
