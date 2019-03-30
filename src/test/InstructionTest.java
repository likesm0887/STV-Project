import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class InstructionTest {

    @Test
    public void  newInstructionTest()
    {
        String  testString = "{45  6}";
         Optional<String> eventParameter = Optional.of(testString);
         Optional<String> elementParameter =Optional.of(testString);
        Instruction instruction = new Instruction("MainActivity","Type_Text","SETTING_BUTTON",eventParameter,elementParameter);
        assertEquals("{45  6}",instruction.getEventParameter().get());
        Instruction instructionHaveNull = new Instruction("MainActivity","Type_Text","SETTING_BUTTON",Optional.empty(),Optional.empty());
        assertEquals(Optional.empty(),instructionHaveNull.getEventParameter());

    }

}