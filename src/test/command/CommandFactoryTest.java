package command;

import adapter.DeviceDriver;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class CommandFactoryTest {
    Mockery context = new JUnit4Mockery();

    @Test
    public void createClickCommand() {

        DeviceDriver mockDriver = context.mock(DeviceDriver.class);
        String xPath = "//*[@class='a']";
        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command clickCommand = commandFactory.createClickCommand(xPath);

        assertTrue(clickCommand instanceof ClickCommand);
    }

    @Test
    public void createTypeTextCommand() {

        DeviceDriver mockDriver = context.mock(DeviceDriver.class);
        String xPath = "//*[@class='a']";
        String text = "fuck up";

        CommandFactory commandFactory = new CommandFactory(mockDriver);

        Command typeTextCommand = commandFactory.createTypeTextCommand(xPath, text);
        assertTrue(typeTextCommand instanceof TypeTextCommand);
    }


}