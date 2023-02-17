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

}
