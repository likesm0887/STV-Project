package adapter.scriptGenerator;

import adapter.Instruction;
import adapter.parser.ScriptParser;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import useCase.command.ClickCommand;
import useCase.command.Command;
import useCase.command.NullCommand;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(JMock.class)
public class ScriptGeneratorTest {
    Mockery context = new JUnit4Mockery();

    String instruction = "View\tClick{}\tfolder_list{0}";

    class isInstanceOfMatcher extends TypeSafeMatcher<Command> {
        Class iface;
        public isInstanceOfMatcher(Class iface) {
            this.iface = iface;
        }

        @Override
        protected boolean matchesSafely(Command item) {
            String expectedClassName = iface.getName();
            String actualClassName = item.getClass().getName();

            return expectedClassName.equals(actualClassName);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Command is instance of: ")
            .appendValue(iface.getName());
        }

        @Override
        public void describeMismatchSafely(Command actual, Description mismatchDescription) {
            mismatchDescription.appendText("actually is instance of: ")
                    .appendValue(actual.getClass().getName());

        }

    }

    public isInstanceOfMatcher isInstanceOf(Class className) {
        return new isInstanceOfMatcher(className);
    }

    @Test
    public void mappingGivenInstructionIntoCommand() {
        ICommandMapper mockGenerator = context.mock(ICommandMapper.class);

        ScriptGenerator scriptGenerator = new ScriptGenerator(mockGenerator);
        Instruction expectedInstrction = new Instruction("View", "Click{}", "folder_list", Optional.empty(), Optional.ofNullable("0"));

        context.checking(new Expectations() {{
            allowing(mockGenerator).mappingFrom(expectedInstrction);
            will(returnValue(new ClickCommand(null, "")));
        }});

        Command command = scriptGenerator.mappingCommandFor(instruction);

        assertThat(command, isInstanceOf(ClickCommand.class));
    }

    @Test
    public void saveInstruction() {
        ScriptGenerator scriptGenerator = new ScriptGenerator();
        scriptGenerator.saveInstruction(instruction);
        scriptGenerator.saveInstruction("test");

        Collection<String> instructions = scriptGenerator.getInstructions();
        assertThat(instructions, hasItems(instruction, "test"));
    }

    @Test
    public void removeCurrentInstruction() {
        ScriptGenerator scriptGenerator = new ScriptGenerator();
        scriptGenerator.saveInstruction(instruction);
        scriptGenerator.removeCurrentInstruction();
        Collection<String> instructions = scriptGenerator.getInstructions();
        assertThat(instructions, not(hasItem(instruction)));
    }

    @Test
    public void executeInstruction() {
        ICommandMapper mockGenerator = context.mock(ICommandMapper.class);
        Command command = new NullCommand();

        ScriptGenerator scriptGenerator = new ScriptGenerator(mockGenerator);
        Instruction expectedInstrction = new Instruction("View", "Click{}", "folder_list", Optional.empty(), Optional.ofNullable("0"));

        context.checking(new Expectations() {{
            exactly(3).of(mockGenerator).mappingFrom(expectedInstrction);
            will(returnValue(command));
        }});

        scriptGenerator.executeInstruction(instruction);
        scriptGenerator.executeInstruction(instruction);
        scriptGenerator.executeInstruction(instruction);
    }

    @Test
    public void formatInstruction() {
        ScriptGenerator scriptGenerator = new ScriptGenerator();
        scriptGenerator.saveInstruction("123");
        scriptGenerator.saveInstruction("234");
        scriptGenerator.saveInstruction("456");
        scriptGenerator.saveInstruction("789");

        String format = scriptGenerator.formatInstructions();

        assertThat(format, allOf(containsString("123"),
                                 containsString("234"),
                                 containsString("456"),
                                 containsString("789")));
    }

    // View	Click{}	folder_list{0}: instruction
    @Test
    public void transformScriptToInstruction() {
        ScriptGenerator scriptGenerator = new ScriptGenerator();

        Instruction transformdInstruction =  scriptGenerator.transformScriptToInstruction(instruction);

        Instruction expectedInstrction = new Instruction("View", "Click{}", "folder_list", Optional.empty(), Optional.ofNullable("0"));

        assertThat(transformdInstruction, equalTo(expectedInstrction));
    }
}