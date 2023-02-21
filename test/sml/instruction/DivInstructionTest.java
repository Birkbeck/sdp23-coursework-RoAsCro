package sml.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class DivInstructionTest extends AbstractInstructionTest{
    @Test
    void executeValidBothPositive() {
        registers.set(EAX, 6);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(3, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidDivisorNegative() {
        registers.set(EAX, 6);
        registers.set(EBX, -2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(-3, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidDividendNegative() {
        registers.set(EAX, -6);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(-3, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidBothNegative() {
        registers.set(EAX, -6);
        registers.set(EBX, -2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(3, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidNonIntegerResult() {
        registers.set(EAX, 3);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void executeDivideByZero() {
        registers.set(EAX, 1);
        registers.set(EBX, 0);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new DivInstruction("x", EAX, EBX);
        assertEquals("x: div EAX EBX", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        assertEquals("div EAX EBX", instruction.toString());
    }
}
