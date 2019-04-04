import adapter.device.AppiumDriver;
import adapter.ConfigReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestCaseRecondMain {
    public static void main(String[] args) throws Exception {
        ConfigReader configReader = new ConfigReader();
        AppiumDriver appium = new AppiumDriver(configReader.getConfig());

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        String xpath="";


        do {
            try
            {
                System.out.print("Please input a  xpath: ");
                xpath = buf.readLine();
                System.out.printf("find %s", appium.findElement(xpath));
            }
            catch(Exception e)
            {
                System.out.println("Not find");
            }
            finally {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        }
        while (!xpath.equals("exit"));

    }
    //*[@text='My tasks']
}
