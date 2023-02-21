package sml.instruction;

import sml.Machine;
import sml.RegisterName;

/**
 * Concrete implementation of the abstract ArithmeticOperationInstruction class.
 * This instruction takes two register names r and s.
 * When executed, the value store at s will be subtracted from the value store at r, and the result stored in r.
 *
 * @author Roland Crompton
 */
public class SubInstruction extends BiRegisterInstruction {

    public final static String OP_CODE = "sub";

    /**
     * Constructor: takes two registers r and s. The value of s is to be subtracted from r.
     *
     * @param label optional label (can be null)
     * @param result a not null RegisterName where the result will be stored and whose value will be subtracted from
     * @param source a not null RegisterName whose value will be the subtrahend
     */
    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Takes machine m as an argument.
     *  Upon executing will subtract the value of register s from the value of register r and store it in register r.
     *
     * @param m the not null machine the instruction runs on
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        return super.execute(m, (one, two) -> one - two);
    }

}
