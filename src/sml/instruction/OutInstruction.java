package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * Concrete implementation of the abstract Instruction class.
 * This instruction takes a register name r.
 * When executed, the value store in r will be printed on the console.
 *
 * @author Roland Crompton
 */

public class OutInstruction extends Instruction {

    private final RegisterName reg;
    public static final String OP_CODE = "out";

    /**
     *  Constructor: takes the RegisterName of the register to be printed.
     *
     * @param label  optional label (can be null)
     * @param reg   the not-null name of the RegisterName to be printed
     */
    public OutInstruction(String label, RegisterName reg) {
        super(label, OP_CODE);
        this.reg = reg;
    }

    /**
     * Prints the current value of the register given at construction on the console.
     *
     * @param m the not null machine the instruction runs on
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        System.out.println(m.getRegisters().get(reg));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Overridden toString method.
     *
     * @return a string in the format "l: out r" where l is the label if any, and r is the register.
     */
    @Override
    public String toString() {
        return getLabelString() + OP_CODE + " " + reg;
    }
}
