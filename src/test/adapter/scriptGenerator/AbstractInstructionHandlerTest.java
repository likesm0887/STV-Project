package adapter.scriptGenerator;

import adapter.Instruction;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AbstractInstructionHandlerTest {
    @Test
    public void transformScriptToInstruction() {

        String instruction = "View\tClick{}\tfolder_list{0}";
        SingleInstructionHandler instructionHandler = new SingleInstructionHandler(new ArrayList<>(), null);

        Instruction transformdInstruction =  instructionHandler.transformScriptToInstruction(instruction);
        Instruction expectedInstruction = Instruction.createTernaryInstruction("View", "Click{}", "folder_list", null, "0");

        assertThat(transformdInstruction, equalTo(expectedInstruction));
    }

}