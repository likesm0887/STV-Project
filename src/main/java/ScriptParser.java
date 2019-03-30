import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    private FileReader scriptFile;
    private BufferedReader bufferedReader;
    private List<Instruction> instructions = new ArrayList<>();


    public List<Instruction> parse(String ScriptPath) throws Exception {
        String event;
        String activity;
        String attribute;
        readFile(ScriptPath);
        this.bufferedReader = new BufferedReader(scriptFile);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            List<String> temp = new ArrayList<>(Arrays.asList(line.split("\t+")));
            if (!"".equals(temp.get(0))) {
                activity = temp.get(0);
                event = temp.get(1);
                attribute = temp.get(2);
                instructions.add(new Instruction(activity, scriptFilterToParameter(event), scriptFilterToParameter(attribute), Optional.ofNullable(curlyBracketsFilter(event)), Optional.ofNullable(curlyBracketsFilter(attribute))));
            }
        }
        return instructions;
    }

    private void readFile(String ScriptPath) throws Exception {
        scriptFile = new FileReader(ScriptPath);
        if (!scriptFile.ready()) throw new Exception("Script Path cannot find");
    }

    private String scriptFilterToParameter(String processString) {
        return processString.replace("{" + curlyBracketsFilter(processString) + "}", "");
    }

    private String curlyBracketsFilter(String processString) {
        String str = "(?<=\\{)(.+?)(?=\\})";
        Pattern pattern = Pattern.compile(str);
        try {
            Matcher matcher = pattern.matcher(processString);
            matcher.find();
            return matcher.group(1);
        } catch (Exception e) {
            return null;
        }
    }
}
