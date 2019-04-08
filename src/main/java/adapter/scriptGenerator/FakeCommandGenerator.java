package adapter.scriptGenerator;

import adapter.device.AppiumDriver;
import entity.Config;
import useCase.command.Command;
import useCase.command.CommandFactory;

public class FakeCommandGenerator implements CommandGenerator {
    protected AppiumDriver appiumDriver;

    public FakeCommandGenerator() {

    }

    public FakeCommandGenerator(Config config) {
        this.appiumDriver = new AppiumDriver(config);
    }

    @Override
    public Command mappingFrom(String instruction) {
        CommandFactory commandFactory = new CommandFactory(appiumDriver);
        return commandFactory.createClickCommand("//*[@class='android.widget.TextView' and @resource-id='android:id/title']");
    }
}
