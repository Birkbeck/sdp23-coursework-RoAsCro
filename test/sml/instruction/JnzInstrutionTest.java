package sml.instruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.Register.*;

import sml.Instruction;
import sml.Translator;

import java.io.IOException;

public class JnzInstrutionTest extends AbstractInstructionTest{

    @Test
    void executeValid() throws IOException {
        Translator translator = new Translator("./cw/src/test4.sml");
        translator.readAndTranslate(machine.getLabels(), machine.getProgram());
        machine.execute();
        assertEquals(0, machine.getRegisters().get(EAX));
        assertEquals(0, machine.getRegisters().get(EBX));

    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new JnzInstruction("x", EAX, "y");
        assertEquals("x: jnz EAX y", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new JnzInstruction(null, EAX, "y");
        assertEquals("jnz EAX y", instruction.toString());
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(new JnzInstruction("x", EAX, "a"), new JnzInstruction("x", EAX, "a"));
        Assertions.assertEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction(null, EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction("x", EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, ECX, "a"), new JnzInstruction(null, EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction(null, EAX, "b"));

    }

}
