package Adapter;

import java.io.IOException;

public interface DeviceDriver {
    void find();
    void click();
    void launchApp() throws IOException, InterruptedException;
}
