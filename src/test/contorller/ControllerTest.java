package contorller;

import Adapter.AppiumAdapter;
import Adapter.DeviceDriver;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void launchApp() throws IOException, InterruptedException {
        DeviceDriver appiumAdapter = new AppiumAdapter();
        appiumAdapter.launchApp();
        appiumAdapter.find();
        appiumAdapter.click();
    }
}