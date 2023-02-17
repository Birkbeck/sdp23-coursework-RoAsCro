package sml.instruction;

import org.junit.jupiter.api.AfterEach;
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

}
