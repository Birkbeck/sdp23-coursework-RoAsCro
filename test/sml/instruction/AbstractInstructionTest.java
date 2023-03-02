package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import sml.Machine;
import sml.Registers;

public abstract class AbstractInstructionTest {

    protected Machine machine;
    protected Registers registers;

    @BeforeEach
    protected void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    protected void tearDown() {
        machine = null;
        registers = null;
    }

}
