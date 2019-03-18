package asserter;

import io.appium.java_client.AppiumDriver;

public class Assert {
    private AppiumDriver driver;
    public  Assert(AppiumDriver driver)
    {
        this.driver = driver;
    }
    public void AsserEqualByText(String except)
    {
        String xmlSouce ;
        xmlSouce = driver.getPageSource();
        if (!xmlSouce.contains(except))
        {
            throw new  NullPointerException();
        }
    }
}
