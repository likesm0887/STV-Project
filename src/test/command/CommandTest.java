package command;

import adapter.DeviceDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(JMock.class)
public class CommandTest {
    protected Mockery context = new JUnit4Mockery();
    protected String xPath = "//*[@class='a']";

    @Test
    public void DriverClickElementWhenClickCommandExecute() {
        DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
        ClickCommand clickCommand = new ClickCommand(mockDeviceDriver, xPath);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).clickElement(xPath);
        }});

        clickCommand.execute();
    }

    @Test
    public void DriverTypeTextElementWhenTypeTextCommandExecute() {
        String text = "my bitch";

        DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
        TypeTextCommand typeTextCommand = new TypeTextCommand(mockDeviceDriver, xPath, text);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).typeText(xPath, text);
        }});

        typeTextCommand.execute();
    }

//    MobileElement findElement(String xPath);
//    List<MobileElement> findElements(String xPath);
//    void launchApplication();
//    void waitUntilElementShow(String xPath);

    @Test
    public void DriverSwipeElementWhenSwipeElementCommandExecute() {
        DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);

        SwipeElementCommand swipeElementCommand = new SwipeElementCommand(mockDeviceDriver, xPath, SwipeElementDirection.UP);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).swipeElement(xPath, SwipeElementDirection.UP);
        }});

        swipeElementCommand.execute();
    }

    @Test
    public void DriverRotationWhenRotationCommandExecute() {
        DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
        RotationCommand rotationCommand = new RotationCommand(mockDeviceDriver , ScreenOrientation.LANDSCAPE);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).rotation(ScreenOrientation.LANDSCAPE);
        }});

        rotationCommand.execute();
    }

    @Test
    public void DriverRestartAppWhenRestartCommandExecute() {
        DeviceDriver mockDeviceDriver = context.mock(DeviceDriver.class);
        RestartCommand restartCommand = new RestartCommand(mockDeviceDriver);

        context.checking(new Expectations() {{
            oneOf(mockDeviceDriver).restartApp();
        }});

        restartCommand.execute();
    }

}