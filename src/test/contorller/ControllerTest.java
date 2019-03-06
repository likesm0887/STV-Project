package contorller;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void launchApp() throws IOException {
        Controller controller = new Controller();
        controller.launchApp();
    }
}