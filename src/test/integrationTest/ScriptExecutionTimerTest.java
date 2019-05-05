
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


        assertOffsetLessThanFourSecond(scriptExecutionTimer, SLEEPING_TIME);
    }

    private void assertOffsetLessThanFourSecond(ScriptExecutionTimer scriptExecutionTimer, long sleepingTime) {
        double elapsedTime = scriptExecutionTimer.elapsedTime();

        double offset = (elapsedTime - (double)sleepingTime) / 1000000000;


        double FOUR_SECOND = 4.0;

        assertThat(offset, lessThanOrEqualTo(FOUR_SECOND));
    }

}