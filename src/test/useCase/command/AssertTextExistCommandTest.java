package useCase.command;

import adapter.device.DeviceDriver;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class AssertTextExistCommandTest {


    private Mockery context = new JUnit4Mockery();

    @Test
    public void shouldCommandDelegateAssertTaskToDriver() {
        String text = "";
        DeviceDriver deviceDriver = context.mock(DeviceDriver.class);

        AssertTextExistCommand command = new AssertTextExistCommand(deviceDriver, text);

        context.checking(new Expectations() {{
            oneOf(deviceDriver).assertTextExist(text);
        }});

        command.execute();
    }

}