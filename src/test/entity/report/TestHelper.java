package entity.report;

import entity.ScriptInformation;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {
    static ScriptInformation createCorrectScriptInformation() {
        ScriptInformation scriptInformation = new ScriptInformation("Test Name");
        scriptInformation.setExecutionTime(1.0E10);
        scriptInformation.executionComplete();
        return scriptInformation;
    }

    static ScriptInformation createFailedScriptInformation() {
        ScriptInformation scriptInformation = new ScriptInformation("Test Name");
        scriptInformation.setExecutionTime(1.0E10);
        scriptInformation.executionFailed();
        return scriptInformation;
    }

    static List<ScriptInformation> createScriptInformationList() {
        List<ScriptInformation> list = new ArrayList<>();
        list.add(createCorrectScriptInformation());
        list.add(createFailedScriptInformation());
        return list;
    }
}
