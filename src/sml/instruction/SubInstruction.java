package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class SubInstruction extends ArithmeticOperationInstruction {

    private final static String OP_CODE = "sub";
    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     */
    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        return super.execute(m, (one, two) -> one - two);
    }

    @Override
    public String toString() {
        return null;
    }
}
