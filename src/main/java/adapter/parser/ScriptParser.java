package adapter.parser;

import adapter.Instruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    private FileReader scriptFile;
    private BufferedReader bufferedReader;
    private List<Instruction> instructions = new ArrayList<>();

    public ScriptParser() { }

    public ScriptParser(String ScriptPath) throws Exception {
        readFile(ScriptPath);
    }

    private List<String> filterTabAndSpace(String scriptForOneLine) {
        return new ArrayList<>(Arrays.asList(scriptForOneLine.split("\\t|\\s{3,4}")));
    }

    public Instruction parseForOneLine(String scriptForOneLine) {
        String event;
        String activity;
        String attribute;
        List<String> filteredString = filterTabAndSpace(scriptForOneLine);
        try {

            if (!"".equals(filteredString.get(0))) {
                if (filteredString.size() == 1) {
                    event = filteredString.get(0);
                    return new Instruction("", scriptFilterToParameter(event),
                            "", Optional.ofNullable(curlyBracketsFilter(event)), Optional.empty());
                } else {
                    activity = filteredString.get(0);
                    event = filteredString.get(1);
                    attribute = filteredString.get(2);
                    return (new Instruction(activity,
                            scriptFilterToParameter(event),
                            scriptFilterToParameter(attribute),
                            Optional.ofNullable(curlyBracketsFilter(event)),
                            Optional.ofNullable(curlyBracketsFilter(attribute))));
                }
            }
        }catch (Exception e)
        {
             throw new RuntimeException(scriptForOneLine+" is error");

        }
        return null;
    }

    public List<Instruction> parse() throws Exception {
        this.bufferedReader = new BufferedReader(scriptFile);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (!line.isEmpty()) {
                instructions.add(parseForOneLine(line));
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
        String regularExpression = "(?<=\\{)(.+?)(?=\\})";
        Pattern pattern = Pattern.compile(regularExpression);
        try {
            Matcher matcher = pattern.matcher(processString);
            matcher.find();
            return matcher.group(1);
        } catch (Exception e) {
            return null;
        }
    }

}
