import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ScriptParserTest {
    private ScriptParser scriptParser;
    private List<Instruction> instructions;
    @Before
    public void SetUp()
    {
        scriptParser = new ScriptParser();

    }
    @Test
    public void parse() throws Exception {


        instructions = scriptParser.parse(System.getProperty("user.dir")+"\\scriptForTest.txt");

        Assert.assertEquals(instructions.get(0).getActivity(),"MainActivity");
        Assert.assertEquals(instructions.get(0).getEvent(),"Click");
        Assert.assertEquals(instructions.get(0).getAttribute(),"TASK_LABEL");
        Assert.assertEquals(instructions.get(1).getActivity(),"MainActivity");
        Assert.assertEquals(instructions.get(1).getEvent(),"Click");
        Assert.assertEquals(instructions.get(1).getAttribute(),"SETTING_BUTTON");
        Assert.assertEquals(instructions.get(1).getParameter(),"1");
        Assert.assertEquals(instructions.size(),8);
    }

    @Test(expected = Exception.class)
    public void parseforNotFindFile() throws Exception {
        try {
            instructions = scriptParser.parse("123");
        } catch (Exception e) {
            throw new Exception("系統找不到檔案");
        }
    }
}