package useCase.command;

import adapter.device.DeviceDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.ScreenOrientation;

import java.util.List;
import java.util.ArrayList;

@RunWith(JMock.class)
public class CommandTest {
    protected Mockery context = new JUnit4Mockery();
    private DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);

    protected String xPath = "//*[@class='a']";

    @Test
    public void DriverClickElementWhenClickCommandExecute() {
        ClickCommand clickCommand = new ClickCommand(mockDeviceDriver, xPath);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).waitAndClickElement(xPath);
        }});

        clickCommand.execute();
    }

    @Test
    public void DriverTypeTextElementWhenTypeTextCommandExecute() {
        String text = "my bitch";

        TypeTextCommand typeTextCommand = new TypeTextCommand(mockDeviceDriver, xPath, text);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).waitAndTypeText(xPath, text);
        }});

        typeTextCommand.execute();
    }

    // need to modify interface
    @Test
    public void DriverSwipeElementWhenSwipeElementCommandExecute() {
        SwipeElementDirection swipeElementDirection = SwipeElementDirection.UP;
        int offset = 10;

        SwipeElementCommand swipeElementCommand = new SwipeElementCommand(mockDeviceDriver, xPath, swipeElementDirection, offset);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).swipeElement(xPath, swipeElementDirection, offset);
        }});

        swipeElementCommand.execute();
    }

    @Test
    public void DriverRotationWhenRotationCommandExecute() {
        ScreenOrientation screenOrientation = ScreenOrientation.LANDSCAPE;
        RotationCommand rotationCommand = new RotationCommand(mockDeviceDriver , screenOrientation);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).rotate(screenOrientation);
        }});

        rotationCommand.execute();
    }

    @Test
    public void DriverRestartAppWhenRestartCommandExecute() {
        RestartCommand restartCommand = new RestartCommand(mockDeviceDriver);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).restartApp();
        }});

        restartCommand.execute();
    }

    @Test
    public void DriverLaunchAppWhenLaunchCommandExecute() {
        LaunchCommand launchCommand = new LaunchCommand(mockDeviceDriver);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).launchApplication();
        }});

        launchCommand.execute();
    }

    @Test
    public void DriverFindElementWhenFindCommandExecute() {
        FindElementCommand findCommand = new FindElementCommand(mockDeviceDriver, xPath);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).findElement(xPath);
            will(returnValue(with(any(MobileElement.class))));
        }});

        findCommand.execute();
    }

    @Test
    public void DriverFindElementsWhenFindElementListExecute() {
        FindElementListCommand findElementListCommand = new FindElementListCommand(mockDeviceDriver, xPath);


        List<MobileElement> mobileElementList = new ArrayList<MobileElement>();

        context.checking(new Expectations() {{

            oneOf(mockDeviceDriver).findElements(xPath);
            will(returnValue(mobileElementList));
        }});

        findElementListCommand.execute();
    }

}