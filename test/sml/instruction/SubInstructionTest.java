package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;

public class SubInstructionTest extends AbstractInstructionTest {

    @Test
    void executeValidBothPositive() {
        registers.set(EAX, 3);
        registers.set(EAX, 2);
        //Instruction instruction = new SunInstruction()
    }

}
