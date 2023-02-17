package sml.instruction;

import sml.Machine;
import sml.RegisterName;

public class MulInstruction extends ArithmeticOperationInstruction {

    public static final String OP_CODE = "mul";

    /**
     * Constructor: takes an optional label, opcode, and two RegisterNames corresponding to registers in some machine.
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     * @param result the RegisterName of the register storing the first operand and where the result will be stored
     * @param source the RegisterName of the register storing the second operand.
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine machine) {
        return 0;
    }
}
