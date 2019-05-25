package entity;

import adapter.device.DeviceDriver;
import useCase.command.ICommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnomalyScriptRunner extends ScriptRunner {
    private Set<String> previousActivities;
    private DeviceDriver driver;

    public AnomalyScriptRunner(List<ICommand> commands, String sourcePath, DeviceDriver driver) {
        super(commands, sourcePath);
        previousActivities = new HashSet<>();
        this.driver = driver;
    }

    protected void afterCommand() {
        String currentActivity = driver.getActivityName();
        System.out.println(currentActivity);
        if (!previousActivities.contains(currentActivity)) {
            driver.pauseApp();
            driver.reopenApp();
            driver.rotate();
            driver.rotate();
            previousActivities.add(currentActivity);
        }
    }
}