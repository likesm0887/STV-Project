package useCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import report.ReportGenerator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class ScriptResultTest {


    Mockery context = new JUnit4Mockery();

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
    }

    @Test
    public void reportGenerateSuccessContentWhenRunSuccessScript() {
        TimerStub timerStub = new TimerStub();
        ReportGenerator mockGenerator = context.mock(ReportGenerator.class);

        ScriptResult scriptResult = new ScriptResult(timerStub, mockGenerator);

        context.checking(new Expectations() {{
            oneOf(mockGenerator).generateScriptInfoHeader(with(any(ScriptInformation.class)));
            oneOf(mockGenerator).generateScriptInfoFooter(with(any(ScriptInformation.class)));
        }});

        scriptResult.scriptStarted("Add Task Quickly");
        scriptResult.scriptEnded();
    }



    @Test
    public void reportGenerateFailureContentWhenRunFailureScript() {
        TimerStub timerStub = new TimerStub();
        ReportGenerator mockGenerator = context.mock(ReportGenerator.class);

        ScriptResult scriptResult = new ScriptResult(timerStub, mockGenerator);

        context.checking(new Expectations() {{
            oneOf(mockGenerator).generateScriptInfoHeader(with(any(ScriptInformation.class)));
            oneOf(mockGenerator).generateScriptInfoBody(with(any(ScriptInformation.class)));
            oneOf(mockGenerator).generateScriptInfoFooter(with(any(ScriptInformation.class)));
        }});

        scriptResult.scriptStarted("Add Task Quickly");
        scriptResult.scriptFailed("Error occur");
    }
}