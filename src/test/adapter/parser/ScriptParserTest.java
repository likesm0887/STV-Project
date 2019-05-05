package adapter.parser;

import adapter.Instruction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ScriptParserTest {
    private final String SCRIPT_PATH = "./src/test/resources/scriptForTest.txt";
    private ScriptParser scriptParser;
    private List<Instruction> instructions;

    @Before
    public void SetUp() throws Exception {
        scriptParser = new ScriptParser();
    }

    @Test
    public void parseForOneLine() {
        ScriptParser scriptParserForOneLine = new ScriptParser();
        instructions = new ArrayList<>();
        instructions.add(scriptParserForOneLine.parseLineOfScript("createTasks\tTypeText{Add Task}\ttitle_editText"));
        Assert.assertEquals(instructions.get(0).getActivity(), "createTasks");
        Assert.assertEquals(instructions.get(0).getEvent(), "TypeText");
        Assert.assertEquals(instructions.get(0).getElement(), "title_editText");
    }

    @Test
    public void parse() throws Exception {
        instructions = scriptParser.parse(SCRIPT_PATH);
        Assert.assertEquals(instructions.get(0).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(0).getEvent(), "Click");
        Assert.assertEquals(instructions.get(0).getElement(), "TASK_LABEL");
        Assert.assertEquals(instructions.get(0).getEventParameter(), Optional.empty());
        Assert.assertEquals(instructions.get(0).getElementParameter().get(), "5");
        Assert.assertEquals(instructions.get(1).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(1).getEvent(), "Type_Text");
        Assert.assertEquals(instructions.get(1).getEventParameter().get(), "45 6@");
        Assert.assertEquals(instructions.get(1).getElement(), "SETTING_BUTTON");
        Assert.assertEquals(instructions.size(), 11);
    }

    @Test(expected = Exception.class)
    public void parseforNotFindFile() throws Exception {
        instructions = scriptParser.parse("123");
    }

    @Test
    public void extractEventOrElementArgumentTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScriptParser.class.getDeclaredMethod("getEventOrElementArgument", String.class);
        method.setAccessible(true);
        String testString = "Type_Text{45 6@}";
        Object[] arguments = new Object[]{testString};
        assertEquals("45 6@", method.invoke(scriptParser, arguments));
        testString = "SETTING_BUTTON{1}";
        arguments = new Object[]{testString};
        assertEquals("1", method.invoke(scriptParser, arguments));
        testString = "SETTING_BUTTON";
        arguments = new Object[]{testString};
        assertEquals(null, method.invoke(scriptParser, arguments));
    }

    @Test
    public void getEventOrElementKeywordTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScriptParser.class.getDeclaredMethod("getEventOrElementKeyword", String.class);
        method.setAccessible(true);
        String testString = "Type_Text{45 6@}";
        Object[] arguments = new Object[]{testString};
        assertEquals("Type_Text", method.invoke(scriptParser, arguments));
        testString = "Type_Text";
        arguments = new Object[]{testString};
        assertEquals("Type_Text", method.invoke(scriptParser, arguments));
    }
}