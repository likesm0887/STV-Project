package adapter.scriptGenerator;

import adapter.parser.ScriptParser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class SingleInstructionHandlerTest {


    protected SingleInstructionHandler singleInstructionHandler;
    protected Mockery context = new JUnit4Mockery();
    protected ICommandMapper commandMapper;

    @Before
    public void setUp() {
        commandMapper = context.mock(ICommandMapper.class);
        singleInstructionHandler = new SingleInstructionHandler(new ArrayList<>(), commandMapper);
    }

    @Test
    public void executionInstruction() {
        String instruction = "View\tClick{}\tfolder_list{0}";


        context.checking(new Expectations() {{
            oneOf(commandMapper).toCommandList(singleInstructionHandler.transformScriptToInstruction(instruction));
        }});


        singleInstructionHandler.executeInstruction(instruction);

        List<String> instructions = singleInstructionHandler.getInstructions();

        assertThat(instructions.size(), equalTo(1));
        assertThat(instructions.get(0), equalTo(instruction));

    }

    @Test
    public void removeInstruction() {
        String instruction = "View\tClick{}\tfolder_list{0}";

        context.checking(new Expectations() {{
            ignoring(commandMapper);
        }});

        singleInstructionHandler.executeInstruction(instruction);
        List<String> instructions = singleInstructionHandler.getInstructions();
        assertThat(instructions.size(), equalTo(1));

        singleInstructionHandler.removeInstruction();

        instructions = singleInstructionHandler.getInstructions();
        assertThat(instructions.size(), equalTo(0));

    }
}