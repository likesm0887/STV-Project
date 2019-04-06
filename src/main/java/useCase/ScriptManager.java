package useCase;

import java.util.ArrayList;
import java.util.List;

public class ScriptManager {
    private List<Script> scripts = new ArrayList<>();

    public void add(Script script) {
        scripts.add(script);
    }


    public boolean isExist(Script script) {
        return scripts.contains(script);
    }

    public void execute(Script script) {
        for (Script scriptInList : scripts) {
            if (scriptInList.equals(script)) {
                scriptInList.executeCommands();
                break;
            }
        }
    }
}
