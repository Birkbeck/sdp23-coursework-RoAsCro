package sml.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class MulInstructionTest extends AbstractInstructionTest{
    @Test
    void executeValidBothPositive() {
        registers.set(EAX, 3);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidOneNegative() {
        registers.set(EAX, 3);
        registers.set(EBX, -1);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(-3, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidBothNegative() {
        registers.set(EAX, -3);
        registers.set(EBX, -2);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new SubInstruction("x", EAX, EBX);
        assertEquals("x: mul EAX EBX", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        assertEquals("mul EAX EBX", instruction.toString());
    }

}
