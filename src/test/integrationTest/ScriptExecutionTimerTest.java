
import org.junit.Test;
import useCase.ScriptExecutionTimer;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

public class ScriptExecutionTimerTest {

    @Test
    public void givenSleepingTimeWhenCountingThenOffsetLessEqualThanFiveMiniSeconds() throws InterruptedException {
        final long SLEEPING_TIME = 3000;

        ScriptExecutionTimer scriptExecutionTimer = new ScriptExecutionTimer();

        scriptExecutionTimer.startCounter();

        Thread.sleep(SLEEPING_TIME);

        scriptExecutionTimer.endCounter();


        assertOffsetLessThanFiveMiniSeconds(scriptExecutionTimer, SLEEPING_TIME);
    }

    private void assertOffsetLessThanFiveMiniSeconds(ScriptExecutionTimer scriptExecutionTimer, long sleepingTime) {
        double elapsedTime = scriptExecutionTimer.elapsedTimeInMiniSecond();

        double offset = elapsedTime - (double)sleepingTime;

        double FIVE_MINI_SECONDS = 5.0;

        assertThat(offset, lessThanOrEqualTo(FIVE_MINI_SECONDS));
    }

}