import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
public class ScriptParserTest {
    private ScriptParser scriptParser;
    private List<Instruction> instructions;
    @Before
    public void SetUp() {
        scriptParser = new ScriptParser();

    }

    @Test
    public void parse() throws Exception {
    /*
    MainActivity	Click	TASK_LABEL{5}

    MainActivity	Type_Text{45 6@}	SETTING_BUTTON{1}

    MainActivity	Click	TASK_LABEL

    MainActivity	Click	SETTING_BUTTON

    MainActivity	Type_Text{0}	SETTING_BUTTON

    MainActivity	Click	SETTING_BUTTON{7}

    MainActivity	Click	TASK_LABEL
    MainActivity	Click	SETTING_BUTTON 5
     */

        instructions = scriptParser.parse(System.getProperty("user.dir") + "\\scriptForTest.txt");
        Assert.assertEquals(instructions.get(0).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(0).getEvent(), "Click");
        Assert.assertEquals(instructions.get(0).getAttribute(), "TASK_LABEL");
        Assert.assertEquals(instructions.get(0).getEventParameter(), Optional.empty());
        Assert.assertEquals(instructions.get(0).getElementParameter().get(), "5");
        Assert.assertEquals(instructions.get(1).getActivity(), "MainActivity");
        Assert.assertEquals(instructions.get(1).getEvent(), "Type_Text");
        Assert.assertEquals(instructions.get(1).getEventParameter().get(), "45 6@");
        Assert.assertEquals(instructions.get(1).getAttribute(), "SETTING_BUTTON");
        Assert.assertEquals(instructions.size(), 8);
    }

    @Test(expected = Exception.class)
    public void parseforNotFindFile() throws Exception {
        try {
            instructions = scriptParser.parse("123");
        } catch (Exception e) {
            throw new Exception("系統找不到檔案");
        }
    }

    @Test
    public void rexTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScriptParser.class.getDeclaredMethod("rex", String.class);
        method.setAccessible(true);
        String  testString ="Type_Text{45 6@}";
        Object[] arguments = new Object[]{testString};
        assertEquals("45 6@",  method.invoke(scriptParser,arguments));
        testString= "SETTING_BUTTON{1}";
        arguments = new Object[]{testString};
        assertEquals("1", method.invoke(scriptParser,arguments));
        testString= "SETTING_BUTTON";
        arguments = new Object[]{testString};
        assertEquals(null,  method.invoke(scriptParser,arguments));
//todo invoke
        assertEquals("Type_Text", scriptParser.rexFilter("Type_Text{45 6@}"));
        assertEquals("Type_Text", scriptParser.rexFilter("Type_Text"));
    }
}