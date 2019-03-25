package config;

import config.Config;
import config.ConfigReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigReaderTest {
    private ConfigReader configReader;
    @Before
    public void SetUp()
    {
        configReader = new ConfigReader();
    }
    @Test
    public void readConfigTest()
    {
        Config config =configReader.getConfig();
        Assert.assertEquals(config.getDevicesName(),"Android Emulator");
        Assert.assertEquals(config.getAndroidVersion(),9);
        Assert.assertEquals(config.getAppiumPort(),5500);
    }
}
