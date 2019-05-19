package useCase;

import entity.ScriptInformation;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ScriptInformationTest {

    @Test
    public void scriptFormatSuccessResult() {
        String taskName = "Add Task";
        ScriptInformation scriptInformation = new ScriptInformation(taskName);
        scriptInformation.setExecutionTime(300);
        scriptInformation.executionComplete();

        assertThat(scriptInformation.summary(),
                allOf(containsString("Add Task"),
                containsString("300.0ms"),
                containsString("PASS")));
    }

    @Test
    public void scriptInformationFormatFailedSummary() {
        String taskName = "Delete Task";
        ScriptInformation scriptInformation = new ScriptInformation(taskName);
        scriptInformation.setExecutionTime(0);
        scriptInformation.executionFailed();

        assertThat(scriptInformation.summary(),
                allOf(containsString("Delete Task"),
                        containsString("0.0ms"),
                        containsString("FAILED")));
    }

}
