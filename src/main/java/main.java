import adapter.ConfigReader;
import adapter.device.AppiumDriver;
import entity.Config;
import useCase.ScriptManager;

public class main {

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("\033[0m BLACK");
        System.out.println("\033[31m RED");
        System.out.println("\033[32m GREEN");
        System.out.println("\033[33m YELLOW");
        System.out.println("\033[34m BLUE");
        System.out.println("\033[35m MAGENTA");
        System.out.println("\033[36m CYAN");
        System.out.println("\033[37m WHITE\033[0m");

//printing the results
        String leftAlignFormat = "| %-20s | %-7d | %-7d | %-7d |%n";

        System.out.format("|---------Test Cases with Steps Summary -------------|%n");
        System.out.format("+----------------------+---------+---------+---------+%n");
        System.out.format("| Test Cases           |Passed   |Failed   |Skipped  |%n");
        System.out.format("+----------------------+---------+---------+---------+%n");

        String formattedMessage = "\033[31m" + "TEST_01".trim() + "\033[0m";

        leftAlignFormat = "| %-29s | %-7d | %-7d | %-7d |%n";
//        System.out.print("\033[31m"); // Open print red
        System.out.printf(leftAlignFormat, formattedMessage, 2, 1, 0);
//        System.out.print("\033[0m"); // Close print red
        System.out.format("+----------------------+---------+---------+---------+%n");

        try {
            Config config = new ConfigReader().getConfig();
            AppiumDriver driver = new AppiumDriver(config);
            driver.startService();

            ScriptManager scriptManager = new ScriptManager(config, driver);
            scriptManager.execute();

            System.out.print(scriptManager.summary());

            driver.stopService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
