package adapter.scriptGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ScriptFileHandler {

    private List<String> instructions;

    public ScriptFileHandler(List<String> instructions) {
        this.instructions = instructions;
    }

    public void writeScriptFile(String fileName) {
        String path = new PathHelper().formatPath(fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            String instructions = formatInstructions();
            writer.write(instructions);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String formatInstructions() {
        String result = "";
        System.out.println(instructions);
        for (int i = 0; i < instructions.size(); i++)
            result += (instructions.get(i) + "\n");
        System.out.print(result);
        return result;
    }
}
