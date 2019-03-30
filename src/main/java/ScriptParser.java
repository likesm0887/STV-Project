import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    private FileReader scriptFile;
    private BufferedReader bufferedReader;
    private List<Instruction> instructions = new ArrayList<>();

    public ScriptParser(String ScriptPath) throws Exception {
        readFile(ScriptPath);
    }
    public List<Instruction> parse() throws Exception {
        String event;
        String activity;
        String attribute;
        this.bufferedReader = new BufferedReader(scriptFile);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            List<String> temp = new ArrayList<>(Arrays.asList(line.split("\t+")));
            if (!"".equals(temp.get(0))) {
                if(temp.size()==1 )
                {
                    event = temp.get(0);
                    instructions.add(new Instruction("", scriptFilterToParameter(event), "", Optional.ofNullable(curlyBracketsFilter(event)), Optional.empty()));
                }
                else
                {
                    activity = temp.get(0);
                    event = temp.get(1);
                    attribute = temp.get(2);
                    instructions.add(new Instruction(activity, scriptFilterToParameter(event), scriptFilterToParameter(attribute), Optional.ofNullable(curlyBracketsFilter(event)), Optional.ofNullable(curlyBracketsFilter(attribute))));
                }

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
