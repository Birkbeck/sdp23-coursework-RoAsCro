package sml.instruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sml.Instruction;

import static sml.Registers.Register.*;

public class MulInstructionTest extends AbstractInstructionTest{
    @Test
    public void executeValidBothPositive() {
        registers.set(EAX, 3);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    public void executeValidOneNegative() {
        registers.set(EAX, 3);
        registers.set(EBX, -1);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-3, machine.getRegisters().get(EAX));
    }

    @Test
    public void executeValidBothNegative() {
        registers.set(EAX, -3);
        registers.set(EBX, -2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    public void testToStringWithLabel() {
        Instruction instruction = new MulInstruction("x", EAX, EBX);
        Assertions.assertEquals("x: mul EAX EBX", instruction.toString());
    }


    @Test
    public void testToStringNoLabel() {
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        Assertions.assertEquals("mul EAX EBX", instruction.toString());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(new MulInstruction("x", EAX, EBX), new MulInstruction("x", EAX, EBX));
        Assertions.assertEquals(new MulInstruction(null, EAX, EBX), new MulInstruction(null, EAX, EBX));
        Assertions.assertNotEquals(new MulInstruction(null, EAX, EBX), new MulInstruction("x", EAX, EBX));
        Assertions.assertNotEquals(new MulInstruction(null, ECX, EBX), new MulInstruction(null, EAX, EBX));
    }

    @Test
    public void testHashCode() {
        Assertions.assertEquals(new MulInstruction("x", EAX, EBX).hashCode(), new MulInstruction("x", EAX, EBX).hashCode());
        Assertions.assertEquals(new MulInstruction(null, EAX, EBX).hashCode(), new MulInstruction(null, EAX, EBX).hashCode());
        Assertions.assertNotEquals(new MulInstruction(null, EAX, EBX).hashCode(), new MulInstruction("x", EAX, EBX).hashCode());
        Assertions.assertNotEquals(new MulInstruction(null, ECX, EBX).hashCode(), new MulInstruction(null, EAX, EBX).hashCode());
        Assertions.assertNotEquals(new MulInstruction(null, ECX, EBX).hashCode(), new SubInstruction(null, ECX, EBX).hashCode());
    }

}
