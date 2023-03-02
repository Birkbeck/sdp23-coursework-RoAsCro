package sml.instruction;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import sml.Instruction;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.ECX;

public class OutInstructionTest extends AbstractInstructionTest {
    @Test
    public void executeValid() {
        registers.set(EAX, 1);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
    }

    @Test
    public void testToStringWithLabel() {
        Instruction instruction = new OutInstruction("x", EAX);
        assertEquals("x: out EAX", instruction.toString());
    }


    @Test
    public void testToStringNoLabel() {
        Instruction instruction = new OutInstruction(null, EAX);
        assertEquals("out EAX", instruction.toString());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(new OutInstruction("x", EAX), new OutInstruction("x", EAX));
        Assertions.assertEquals(new OutInstruction(null, EAX), new OutInstruction(null, EAX));
        Assertions.assertNotEquals(new OutInstruction(null, EAX), new OutInstruction("x", EAX));
        Assertions.assertNotEquals(new OutInstruction(null, ECX), new OutInstruction(null, EAX));
    }

    @Test
    public void testHashCode() {
        Assertions.assertEquals(new OutInstruction("x", EAX).hashCode(), new OutInstruction("x", EAX).hashCode());
        Assertions.assertEquals(new OutInstruction(null, EAX).hashCode(), new OutInstruction(null, EAX).hashCode());
        Assertions.assertNotEquals(new OutInstruction(null, EAX).hashCode(), new OutInstruction("x", EAX).hashCode());
        Assertions.assertNotEquals(new OutInstruction(null, ECX).hashCode(), new OutInstruction(null, EAX).hashCode());
    }

}
