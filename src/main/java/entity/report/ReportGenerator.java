package entity.report;

import entity.ScriptInformation;

import java.util.List;

public interface ReportGenerator {
    String generateScriptInfoHeader(ScriptInformation scriptInformation);

    String generateScriptInfoFooter(ScriptInformation scriptInformation);

    String generateScriptInfoBody(ScriptInformation scriptInformation);

    String generateScriptSummary(List<ScriptInformation> scriptInformationList);

    String generateScriptInfoExceptionBody(ScriptInformation scriptInformation);
}
