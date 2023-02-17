package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;


public class MovInstruction extends Instruction {

    public static final String OP_CODE = "mov";

    private final RegisterName reg;

    private final int value;


    /**
     * Constructor: an Instruction that stores the given integer value in the given register.
     *
     * @param label  optional label (can be null)
     * @param reg   the name of the register to store the integer value in
     * @param value the integer value to be stored in the register
     */
    public MovInstruction(String label, RegisterName reg, int value) {
        super(label, OP_CODE);
        this.reg = reg;
        this.value = value;
    }

    @Override
    public int execute(Machine machine) {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
