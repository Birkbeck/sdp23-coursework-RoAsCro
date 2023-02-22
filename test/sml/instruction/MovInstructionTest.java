package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;


public class MovInstructionTest extends AbstractInstructionTest {

    @Test
    void executeValidPositive() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        instruction.execute(machine);
        assertEquals(1, registers.get(EAX));
    }

    @Test
    void executeValidNegative() {
        Instruction instruction = new MovInstruction(null, EAX, -1);
        instruction.execute(machine);
        assertEquals(-1, registers.get(EAX));
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new MovInstruction("x", EAX, 1);
        assertEquals("x: mov EAX 1", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        assertEquals("mov EAX 1", instruction.toString());
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(new MovInstruction("x", EAX, 1), new MovInstruction("x", EAX, 1));
        Assertions.assertEquals(new MovInstruction(null, EAX, 1), new MovInstruction(null, EAX, 1));
        Assertions.assertNotEquals(new MovInstruction(null, EAX, 1), new MovInstruction("x", EAX, 1));
        Assertions.assertNotEquals(new MovInstruction(null, ECX, 1), new MovInstruction(null, EAX, 1));
        Assertions.assertNotEquals(new MovInstruction(null, EAX, 1), new MovInstruction(null, EAX, 2));
    }
}
