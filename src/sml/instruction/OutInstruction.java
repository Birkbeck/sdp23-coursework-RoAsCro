package sml.instruction;

import sml.Instruction;
import sml.Machine;

public class OutInstruction extends Instruction {
    /**
     * 
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     */
    public OutInstruction(String label, String opcode) {
        super(label, opcode);
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
