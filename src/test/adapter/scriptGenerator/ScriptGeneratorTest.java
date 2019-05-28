package adapter.scriptGenerator;

import adapter.Instruction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import useCase.command.Command;
import useCase.command.NullCommand;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class ScriptGeneratorTest {
    Mockery context = new JUnit4Mockery();

    String instruction = "View\tClick{}\tfolder_list{0}";

    @Test
    public void executeInstruction() {
        ICommandMapper mockMapper = context.mock(ICommandMapper.class);
        Command command = new NullCommand();

        ScriptGenerator scriptGenerator = new ScriptGenerator(mockMapper);
        Instruction expectedInstruction = Instruction.createTernaryInstruction("View", "Click{}", "folder_list", null, "0");

        context.checking(new Expectations() {{
            exactly(3).of(mockMapper).toCommandList(expectedInstruction);
            will(returnValue(Arrays.asList(command)));
        }});

        scriptGenerator.executeInstruction(instruction);
        scriptGenerator.executeInstruction(instruction);
        scriptGenerator.executeInstruction(instruction);
    }

    @Test
    public void GivenCertainModeWhenExecuteInstructionThenResponseByMode() {
        ICommandMapper iCommandMapper = context.mock(ICommandMapper.class);
        ScriptGenerator scriptGenerator = new ScriptGenerator(iCommandMapper);


        context.checking(new Expectations() {{
            ignoring(iCommandMapper);
        }});

        scriptGenerator.switchMode(ScriptGenerator.ExecuteMode.Single);


        scriptGenerator.executeInstruction("123");
        scriptGenerator.executeInstruction("123");
        scriptGenerator.executeInstruction("123");
        assertThat(scriptGenerator.instructionSize(), equalTo(3));

        scriptGenerator.switchMode(ScriptGenerator.ExecuteMode.Batch);

        scriptGenerator.executeInstruction("123");
        scriptGenerator.executeInstruction("123");
        scriptGenerator.executeInstruction("123");

        assertThat(scriptGenerator.instructionSize(), equalTo(6));

        scriptGenerator.removeInstruction();

        assertThat(scriptGenerator.instructionSize(), equalTo(3));
    }
}