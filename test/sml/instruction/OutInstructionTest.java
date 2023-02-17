package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.Register.EAX;

public class OutInstructionTest extends AbstractInstructionTest {
    @Test
    void executeValid() {
        registers.set(EAX, 1);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
    }

    @Test
    void testToStringWithLabel() {
        Instruction instruction = new OutInstruction("x", EAX);
        assertEquals("x: out EAX", instruction.toString());
    }


    @Test
    void testToStringNoLabel() {
        Instruction instruction = new OutInstruction(null, EAX);
        assertEquals("out EAX", instruction.toString());
    }

}
