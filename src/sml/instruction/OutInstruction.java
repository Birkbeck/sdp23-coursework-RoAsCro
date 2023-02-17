package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

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
     * @param machine the machine the instruction runs on
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine machine) {
        System.out.println(machine.getRegisters().get(reg));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return null;
    }
}
