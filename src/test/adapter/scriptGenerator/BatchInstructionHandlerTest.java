package adapter.scriptGenerator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BatchInstructionHandlerTest {

    protected BatchInstructionHandler batchInstructionHandler;
    protected Mockery context = new JUnit4Mockery();
    protected ICommandMapper commandMapper;

    @Before
    public void setUp() {
        commandMapper = context.mock(ICommandMapper.class);
        batchInstructionHandler = new BatchInstructionHandler(new ArrayList<>(), commandMapper);
    }

    @Test
    public void executionInstruction() {
        String instruction = "View\tClick{}\tfolder_list{0}";


        context.checking(new Expectations() {{
            oneOf(commandMapper).toCommandList(batchInstructionHandler.transformScriptToInstruction(instruction));
        }});


        batchInstructionHandler.executeInstruction(instruction);

        List<String> instructions = batchInstructionHandler.getInstructions();

        assertThat(instructions.size(), equalTo(1));
        assertThat(instructions.get(0), equalTo(instruction));
    }

    @Test
    public void removeInstruction() {
        String instruction = "View\tClick{}\tfolder_list{0}";

        context.checking(new Expectations() {{
            ignoring(commandMapper);
        }});

        batchInstructionHandler.executeInstruction(instruction);
        batchInstructionHandler.executeInstruction(instruction);
        batchInstructionHandler.executeInstruction(instruction);
        batchInstructionHandler.executeInstruction(instruction);
        batchInstructionHandler.executeInstruction(instruction);

        List<String> instructions = batchInstructionHandler.getInstructions();
        assertThat(instructions.size(), equalTo(5));

        batchInstructionHandler.removeInstruction();

        instructions = batchInstructionHandler.getInstructions();
        assertThat(instructions.size(), equalTo(0));

    }
}