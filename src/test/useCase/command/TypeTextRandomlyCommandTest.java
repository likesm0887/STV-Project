package useCase.command;

import adapter.device.DeviceDriver;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JMock.class)
public class TypeTextRandomlyCommandTest {

    protected Mockery context = new JUnit4Mockery();

    class TypeTextRandomlyCommandStub extends  TypeTextRandomlyCommand {
        public TypeTextRandomlyCommandStub(DeviceDriver deviceDriver, String xPath) {
            super(deviceDriver, xPath);
        }

        public String generateRandomText() {
            return "mYVzy0BPud";
        }
    }

    @Test
    public void shouldDelegateTaskToDeviceDriver() {
        DeviceDriver mockDriver = context.mock(DeviceDriver.class);

        TypeTextRandomlyCommandStub command = new TypeTextRandomlyCommandStub(mockDriver, "");

        context.checking(new Expectations() {{
            oneOf(mockDriver).waitAndTypeText("", "mYVzy0BPud");
        }});

        command.execute();
    }


    @Test
    public void shouldCommandGenerateRandomText_thenEveryTimeGetDiffResult() {
        DeviceDriver EMPTY_DRIVER = null;
        String EMPTY_XPATH = "";
        TypeTextRandomlyCommand command = new TypeTextRandomlyCommand(EMPTY_DRIVER, EMPTY_XPATH);

        List<String> randomText = new ArrayList<>();

        for (int i = 0; i < 100; i ++) {
            randomText.add(command.generateRandomText());
        }


        int ONE_HUNDRED = randomText.size();

        assertThat(randomText, containsInvariantItem(ONE_HUNDRED));
    }

    private ContainInvariantItemMatcher containsInvariantItem(int number) {
        return new ContainInvariantItemMatcher(number);
    }

    public class ContainInvariantItemMatcher extends TypeSafeMatcher<List<String>> {
        private int variantNumber;
        private int expectedNumber;

        public ContainInvariantItemMatcher(int variantNumber) {
            this.variantNumber = variantNumber;
        }

        @Override
        public boolean matchesSafely(List<String> actual) {
            int temp  = variantNumber;
            for (int i = 0; i < actual.size() - 1; i++) {
                if (actual.get(i) != actual.get(i + 1))
                    temp--;
            }

            expectedNumber = variantNumber - temp;

            return temp == 1;
        }

        @Override
        public void describeTo(Description description) {
            String expectedMessage = String.format(" Contains %s invariant item", variantNumber);
            description.appendText(expectedMessage);

        }
    }


}