package sml.instruction;

import sml.Machine;
import sml.RegisterName;

/**
 * Concrete implementation of the abstract ArithmeticOperationInstruction class.
 * This instruction takes two register names r and s.
 * When executed, the value stored at r will be multiplied by the value store at r, and the result stored in r.
 *
 * @author Roland Crompton
 */
public class MulInstruction extends BiRegisterInstruction {

    public static final String OP_CODE = "mul";

    /**
     * Constructor: takes an optional label, and two RegisterNames corresponding to registers in some machine.
     *
     * @param label  optional label (can be null)
     * @param result the not null RegisterName of the register storing the first operand and where the result will
     *               be stored
     * @param source the not null RegisterName of the register storing the second operand.
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Multiplies the values stored in the registers specified at construction together and stores the result in the
     * first register specified at construction.
     *
     * @param m the machine the instruction runs on
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        return super.execute(m, (one, two) -> one * two);
    }
}
