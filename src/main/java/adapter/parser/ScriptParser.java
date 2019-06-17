package adapter.parser;

import adapter.Instruction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    public List<Instruction> parse(String scriptPath) throws Exception {
        checkScriptExists(scriptPath);

        List<Instruction> instructions = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptPath));
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (!line.isEmpty() && !isCommentInstruction(line)) {
                instructions.add(parseLineOfScript(line));
            }
        }
        return instructions;
    }

    private void checkScriptExists(String scriptPath) {
        File script = new File(scriptPath);
        if (!script.exists())
            throw new RuntimeException("Script file" + scriptPath + "does not exist");
        if (script.isDirectory())
            throw new RuntimeException("The given script path is a directory");
    }

    public Instruction parseLineOfScript(String line) {
        if (Objects.nonNull(line) && (line.isEmpty() || isCommentInstruction(line)))
            return null;

        List<String> lineChunks = splitLineOfScript(line);
        if (lineChunks.size() == 1)
            return createUnaryInstruction(lineChunks.get(0));
//        for(String a : lineChunks) System.out.println(a);
        return createTernaryInstruction(lineChunks.get(0), lineChunks.get(1), lineChunks.get(2));
    }

    private Instruction createUnaryInstruction(String event) {
        return Instruction.createUnaryInstruction(getEventOrElementKeyword(event), getEventOrElementArgument(event));
    }

    private Instruction createTernaryInstruction(String activity, String event, String element) {
        return Instruction.createTernaryInstruction(activity,
                getEventOrElementKeyword(event),
                getEventOrElementKeyword(element),
                getEventOrElementArgument(event),
                getEventOrElementArgument(element));
    }

    private List<String> splitLineOfScript(String line) {
        return Arrays.asList(line.split("\\t|\\s{3,4}"));
    }

    private String getEventOrElementKeyword(String input) {
        return input.replaceAll("\\{.+\\}", "");
    }

    private String getEventOrElementArgument(String input) {
        final String regex = "(?<=\\{)(.+?)(?=\\})";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        List<String> allMatches = new ArrayList<>();
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        if (allMatches.size() > 0)
            return allMatches.get(0);
        return null;
    }

    private boolean isCommentInstruction(String line){
        return line.startsWith("#");
    }

}
