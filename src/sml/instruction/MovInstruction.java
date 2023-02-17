package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * Concrete implementation of the abstract Instruction class.
 * This instruction takes a register name r and an integer x.
 * When executed, x will be stored in r.
 *
 * @author Roland Crompton
 */

public class MovInstruction extends Instruction {

    public static final String OP_CODE = "mov";

    private final RegisterName reg;

    private final int value;


    /**
     * Constructor: takes the RegisterName of the register to be used as storage and the integer value to be stored.
     *
     * @param label  optional label (can be null)
     * @param reg   the not-null name of the register to store the integer value in
     * @param value the integer value to be stored in the register
     */
    public MovInstruction(String label, RegisterName reg, int value) {
        super(label, OP_CODE);
        this.reg = reg;
        this.value = value;
    }

    /**
     * Takes machine m as an argument.
     * Upon executing will store the integer given at construction in the register in m
     * corresponding to the RegisterName given at construction.
     *
     * @param m the machine the instruction runs on.
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(reg, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Overridden toString method.
     *
     * @return a string in the format "l: mov r x" where l is the label if any was given, r is the register name, and
     * x is the integer.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + reg.toString() + " " + value;
    }
}
