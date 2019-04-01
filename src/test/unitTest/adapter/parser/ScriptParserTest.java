package adapter.parser;

import adapter.Instruction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

public class ScriptParserTest {
    private ScriptParser scriptParser;
    private List<Instruction> instructions;
    @Before
    public void SetUp() throws Exception {
        scriptParser = new ScriptParser("./src/test/resources/scriptForTest.txt");

    }

    @Test
    public void parse() throws Exception {
        instructions = scriptParser.parse();
        Assert.assertEquals(instructions.get(0).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(0).getEvent(), "Click");
        Assert.assertEquals(instructions.get(0).getAttribute(), "TASK_LABEL");
        Assert.assertEquals(instructions.get(0).getEventParameter(), Optional.empty());
        Assert.assertEquals(instructions.get(0).getElementParameter().get(), "5");
        Assert.assertEquals(instructions.get(1).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(1).getEvent(), "Type_Text");
        Assert.assertEquals(instructions.get(1).getEventParameter().get(), "45 6@");
        Assert.assertEquals(instructions.get(1).getAttribute(), "SETTING_BUTTON");
        Assert.assertEquals(instructions.size(), 11);
    }

    @Test(expected = Exception.class)
    public void parseforNotFindFile() throws Exception {
        scriptParser = new ScriptParser("123");
        try {
            instructions = scriptParser.parse();
        } catch (Exception e) {
            throw new Exception("系統找不到檔案");
        }
    }

    @Test
    public void curlyBracketsFilterTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScriptParser.class.getDeclaredMethod("curlyBracketsFilter", String.class);
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
    public void scriptFilterToParameterTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScriptParser.class.getDeclaredMethod("scriptFilterToParameter", String.class);
        method.setAccessible(true);
        String testString = "Type_Text{45 6@}";
        Object[] arguments = new Object[]{testString};
        assertEquals("Type_Text", method.invoke(scriptParser, arguments));
        testString = "Type_Text";
        arguments = new Object[]{testString};
        assertEquals("Type_Text",  method.invoke(scriptParser,arguments));

    }
}