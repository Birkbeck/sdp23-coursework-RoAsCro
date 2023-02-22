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

public class SubInstructionTest extends AbstractInstructionTest {

    @Test
    void executeValidBothPositive() {
        registers.set(EAX, 3);
        registers.set(EBX, 2);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidSubtrahendNegative() {
        registers.set(EAX, 3);
        registers.set(EBX, -2);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(5, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidBothNegative() {
        registers.set(EAX, -3);
        registers.set(EBX, -2);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(-1, machine.getRegisters().get(EAX));
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new SubInstruction("x", EAX, EBX);
        assertEquals("x: sub EAX EBX", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        assertEquals("sub EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(new SubInstruction("x", EAX, EBX), new SubInstruction("x", EAX, EBX));
        Assertions.assertEquals(new SubInstruction(null, EAX, EBX), new SubInstruction(null, EAX, EBX));
        Assertions.assertNotEquals(new SubInstruction(null, EAX, EBX), new SubInstruction("x", EAX, EBX));
        Assertions.assertNotEquals(new SubInstruction(null, ECX, EBX), new SubInstruction(null, EAX, EBX));
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(new SubInstruction("x", EAX, EBX).hashCode(), new SubInstruction("x", EAX, EBX).hashCode());
        Assertions.assertEquals(new SubInstruction(null, EAX, EBX).hashCode(), new SubInstruction(null, EAX, EBX).hashCode());
        Assertions.assertNotEquals(new SubInstruction(null, EAX, EBX).hashCode(), new SubInstruction("x", EAX, EBX).hashCode());
        Assertions.assertNotEquals(new SubInstruction(null, ECX, EBX).hashCode(), new SubInstruction(null, EAX, EBX).hashCode());
        Assertions.assertNotEquals(new SubInstruction(null, ECX, EBX).hashCode(), new AddInstruction(null, ECX, EBX).hashCode());
    }

}
