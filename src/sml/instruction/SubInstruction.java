package sml.instruction;

import sml.Instruction;
import sml.Machine;

public class SubInstruction extends Instruction {


    private final static String OP_CODE = "sub";
    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     */
    public SubInstruction(String label, String opcode) {
        super(label, OP_CODE);
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
