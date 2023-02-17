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

    @Override
    public int execute(Machine machine) {
        
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return null;
    }
}
