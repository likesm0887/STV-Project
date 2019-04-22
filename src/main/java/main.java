import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import entity.Config;
import useCase.ScriptManager;

public class main {
    public static void main(String[] args) {
        try {
            Config config = new ConfigReader().getConfig();
            AppiumDriver driver = new AppiumDriver(config);
            driver.startService();

            ScriptManager scriptManager = new ScriptManager(config, driver);
            scriptManager.execute();

            driver.stopService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
