package entity.report;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TimeHelperTest {

    final double ONE_SECOND_TIME_STAMP = 1000000000;
    @Test
    public void transformToSecond() {
        double ONE_SECOND = 1;
        double second = TimeHelper.transformToSecond(ONE_SECOND_TIME_STAMP);
        assertThat(second, equalTo(ONE_SECOND));
    }

    @Test
    public void transformToSecondFormat() {
        final String ONE_SECOND = "1.0";
        String second = TimeHelper.transformToSecondFormat(ONE_SECOND_TIME_STAMP);
        assertThat(second, equalTo(ONE_SECOND));
    }


    @Test
    public void transformToMinuteFormat() {
        final String ONE_MINUTE = "1.0";
        String minute = TimeHelper.transformToMinuteFormat(ONE_SECOND_TIME_STAMP * 60);
        assertThat(minute, equalTo(ONE_MINUTE));
    }

}