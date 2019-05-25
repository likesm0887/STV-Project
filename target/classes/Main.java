import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import entity.Config;
import entity.ScriptManager;

public class main {

    public static void main(String[] args) {
        try {
            Config config = new ConfigReader().getConfig();
            AppiumDriver driver = new AppiumDriver(config);
            driver.startService();

            ScriptManager scriptManager = new ScriptManager(config, driver);
            scriptManager.execute();

            driver.stopService();
            showTestReport(scriptManager.summary());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showTestReport(String report) {
        System.out.println(report);
    }
}
