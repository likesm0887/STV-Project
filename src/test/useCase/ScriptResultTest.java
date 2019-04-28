package useCase;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ScriptResultTest {

    private class TimerStub implements ExecutionTimer {

        @Override
        public void startCounter() {

        }

        @Override
        public void endCounter() {

        }

        @Override
        public double elapsedTime() {
            return 0;
        }

        @Override
        public double elapsedTimeInMiniSecond() {
            return 300.0;
        }
    }

    @Test
    public void twoScriptPassFormatting() {
        TimerStub timerStub = new TimerStub();
        ScriptResult scriptResult = new ScriptResult(timerStub);


        scriptResult.scriptStarted("Add Task Quickly");
        scriptResult.scriptEnded();


        scriptResult.scriptStarted("Delete Task");
        scriptResult.scriptEnded();


        String[] result = {"Add Task Quickly", "Delete Task"};


        for (int i = 0; i < result.length; i++) {
            assertThat(scriptResult.summary(),
                    allOf(containsString(result[i]),
                            containsString("300.0ms"),
                            containsString("PASS")));
        }

    }



    @Test
    public void TwoScriptOnePassOneFailedFormatting() {
        TimerStub timerStub = new TimerStub();
        ScriptResult scriptResult = new ScriptResult(timerStub);


        String taskName = "Add Task Quickly";
        scriptResult.scriptStarted(taskName);
        scriptResult.scriptFailed();

        assertThat(scriptResult.summary(),
                allOf(containsString("Add Task Quickly"),
                        containsString("300.0ms"),
                        containsString("FAILED")));
    }

}