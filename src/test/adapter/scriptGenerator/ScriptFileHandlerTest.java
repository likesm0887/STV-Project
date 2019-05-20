package adapter.scriptGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ScriptFileHandlerTest {

    protected ScriptFileHandler scriptFileHandler;

    @Before
    public void setUp() {
        List<String> instruction = new ArrayList<>();
        instruction.add("123");
        instruction.add("234");
        instruction.add("456");
        instruction.add("789");

        scriptFileHandler = new ScriptFileHandler(instruction);
    }

    @Test
    public void formatInstruction() {
        String format = scriptFileHandler.formatInstructions();

        assertThat(format, allOf(containsString("123"),
                containsString("234"),
                containsString("456"),
                containsString("789")));
    }

}