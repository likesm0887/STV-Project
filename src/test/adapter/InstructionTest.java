package adapter;

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
         Instruction instruction = Instruction.createTernaryInstruction("MainActivity","Type_Text","SETTING_BUTTON", testString, testString);
        assertEquals("{45  6}",instruction.getEventParameter().get());
        Instruction instructionHaveNull = Instruction.createTernaryInstruction("MainActivity","Type_Text","SETTING_BUTTON", null, null);
        assertEquals(Optional.empty(),instructionHaveNull.getEventParameter());
    }

}