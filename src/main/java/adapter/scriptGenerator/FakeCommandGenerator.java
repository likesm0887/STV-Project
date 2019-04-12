package adapter.scriptGenerator;

import adapter.Instruction;
import adapter.device.AppiumDriver;
import entity.Config;
import useCase.command.Command;
import useCase.command.CommandFactory;

import java.util.List;

public class FakeCommandGenerator implements CommandGenerator {
    protected AppiumDriver appiumDriver;

    public FakeCommandGenerator() {

    }

    public FakeCommandGenerator(Config config) {
        this.appiumDriver = new AppiumDriver(config);
        appiumDriver.startAppiumService();
    }

    @Override
    @Deprecated
    public Command mappingFrom(String instruction) {
        CommandFactory commandFactory = new CommandFactory(appiumDriver);
        return commandFactory.createClickCommand("//*[@class='android.widget.TextView' and @resource-id='android:id/title']");
    }

    @Override
    public Command mappingFrom(Instruction instruction) {
        return null;
    }

    @Override
    public List<Command> mappingFrom(List<Instruction> instructions) {
        return null;
    }



}
