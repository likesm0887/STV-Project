package adapter.device;

import com.google.common.io.CharSource;
import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class AppiumAsserterTest {

    private InputStream createActivityRecord() throws IOException {
        String input = "\" Run #8: ActivityRecord{1801877 u0 org.dmfs.tasks/.EditTaskActivity t19018}\\n\" +\n";
        InputStream targetStream =
                new ReaderInputStream(CharSource.wrap(input).openStream(), StandardCharsets.UTF_8.name());
        return targetStream;
    }

    @Test
    public void givenActivityRecordWhenParseShouldExtractCorrectActivity() throws NoSuchMethodException, IOException {
        AppiumAsserter appiumAsserter = new AppiumAsserter(null, null);

        InputStream inputStream = createActivityRecord();

        String parsedActivity = appiumAsserter.parseActivityName(inputStream);


        Assert.assertEquals("EditTaskActivity", parsedActivity);
    }

}