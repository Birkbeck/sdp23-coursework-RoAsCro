package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;


public class MovInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

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
}
