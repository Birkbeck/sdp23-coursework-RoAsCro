package sml.instruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.Register.*;

import sml.Instruction;

public class JnzInstrutionTest extends AbstractInstructionTest{

    @Test
    public void executeValid() {
        machine.getProgram().add(new MovInstruction(null, EAX, 3));
        machine.getProgram().add(new MovInstruction(null, EBX, 1));
        machine.getProgram().add(new MovInstruction(null, ESI, 1));
        machine.getProgram().add(new OutInstruction(null, ESI));
        machine.getProgram().add(new MovInstruction(null, ECX, 1));
        machine.getProgram().add(new SubInstruction("a", EAX, EBX));
        machine.getLabels().addLabel("a", 5);
        machine.getProgram().add(new OutInstruction(null, EAX));
        machine.getProgram().add(new JnzInstruction(null, EAX, "a"));
        machine.getProgram().add(new OutInstruction(null, EBX));
        machine.getProgram().add(new JnzInstruction(null, EBX, "b"));
        machine.getProgram().add(new AddInstruction(null, EBX, ECX));
        machine.getProgram().add(new SubInstruction("b", EBX, ECX));
        machine.getLabels().addLabel("b", 11);
        machine.getProgram().add(new OutInstruction(null, EBX));
        machine.execute();
        assertEquals(0, machine.getRegisters().get(EAX));
        assertEquals(0, machine.getRegisters().get(EBX));
        assertEquals(1, machine.getRegisters().get(ESI));

    }

    @Test
    public void executeLabelDoesNotExist() {
        machine.getProgram().add(new MovInstruction(null, EAX, 2));
        machine.getProgram().add(new MovInstruction(null, EBX, 1));
        machine.getProgram().add(new SubInstruction(null, EAX, EBX));
        machine.getProgram().add(new JnzInstruction(null, EAX, "a"));
        machine.getProgram().add(new MovInstruction(null, EAX, 1));
        machine.execute();
        assertEquals(0, machine.getRegisters().get(EAX));
    }

    @Test
    public void testToStringWithLabel() {
        Instruction instruction = new JnzInstruction("x", EAX, "y");
        assertEquals("x: jnz EAX y", instruction.toString());
    }


    @Test
    public void testToStringNoLabel() {
        Instruction instruction = new JnzInstruction(null, EAX, "y");
        assertEquals("jnz EAX y", instruction.toString());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(new JnzInstruction("x", EAX, "a"), new JnzInstruction("x", EAX, "a"));
        Assertions.assertEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction(null, EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction("x", EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, ECX, "a"), new JnzInstruction(null, EAX, "a"));
        Assertions.assertNotEquals(new JnzInstruction(null, EAX, "a"), new JnzInstruction(null, EAX, "b"));
    }

    @Test
    public void testHashCode() {
        Assertions.assertEquals(new JnzInstruction("x", EAX, "a").hashCode(), new JnzInstruction("x", EAX, "a").hashCode());
        Assertions.assertEquals(new JnzInstruction(null, EAX, "a").hashCode(), new JnzInstruction(null, EAX, "a").hashCode());
        Assertions.assertNotEquals(new JnzInstruction(null, EAX, "a").hashCode(), new JnzInstruction("x", EAX, "a").hashCode());
        Assertions.assertNotEquals(new JnzInstruction(null, ECX, "a").hashCode(), new JnzInstruction(null, EAX, "a").hashCode());
        Assertions.assertNotEquals(new JnzInstruction(null, ECX, "a").hashCode(), new JnzInstruction(null, ECX, "b").hashCode());
    }

}
