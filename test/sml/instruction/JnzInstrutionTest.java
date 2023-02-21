package sml.instruction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sml.Instruction;
import sml.Translator;

import java.io.IOException;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class JnzInstrutionTest extends AbstractInstructionTest{

    @Test
    void executeValid() throws IOException {
        Translator translator = new Translator("./cw/src/test4.sml");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        assertEquals(0, machine.getRegisters().get(EAX));
        assertEquals(0, machine.getRegisters().get(EBX));

    }
    
}
