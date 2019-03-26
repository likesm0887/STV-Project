import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScriptParser {
    private FileReader scriptFile;
    private BufferedReader bufferedReader;
    private List<Instruction> instructions = new ArrayList<>();

    public List<Instruction> parse(String ScriptPath) throws Exception {
        readFile(ScriptPath);
        this.bufferedReader = new BufferedReader(scriptFile);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            List<String> temp = new ArrayList<>(Arrays.asList(line.split(" ")));
            if (!"".equals(temp.get(0))) {
                temp.removeAll(Collections.singleton(""));
                if (temp.size() >= 4) {
                    instructions.add(new Instruction(temp.get(0), temp.get(1), temp.get(2), temp.get(3)));
                }
               else {
                instructions.add(new Instruction(temp.get(0), temp.get(1), temp.get(2)));
                }
             }
           }
        return instructions;
    }

    private void readFile(String ScriptPath) throws Exception {
        scriptFile = new FileReader(ScriptPath);
        if (!scriptFile.ready()) throw new Exception("Script Path cannot find");
    }
}
