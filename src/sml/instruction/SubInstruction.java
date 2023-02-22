package sml.instruction;

import sml.Machine;
import sml.RegisterName;

/**
 * A concrete implementation of the abstract BiRegisterInstruction class. An instruction for deducting one value from
 * another.
 * <p></p>
 * The instruction takes two RegisterNames, result and source, at construction.
 * When executed will add the values stored at the two registers, storing the resulting value in result.
 *
 * @author Roland Crompton
 */
public class SubInstruction extends BiRegisterInstruction {

    /**
     * The operation code for all SubInstructions. The name of the operation.
     */
    public final static String OP_CODE = "sub";

    /**
     * Constructs a new SubInstruction with an optional label, a RegisterName result, and a RegisterName source.
     *
     * @param label optional label for the instruction (can be null)
     * @param result a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               subtracted by source on execution, and the resulting value will then be stored result
     * @param source a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               subtracted from result on execution
     */
    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Executes the instruction on the given machine m. Takes the value stored in the Register in m corresponding to
     * RegisterName source defined at construction, subtracts it from the value stored in the Register in m
     * corresponding to RegisterName result defined a construction, and stores the result in the result Register in m.
     * <p></p>
     * The program counter in m will then move onto the next instruction sequentially.
     *
     * @param m the machine the instruction runs on, where the values will be retrieved from and where the result will
     *          be stored
     * @return the normal program counter update indicating the program counter should move onto the next instruction
     * sequentially
     */
    @Override
    public int execute(Machine m) {
        return super.execute(m, (one, two) -> one - two);
    }

}
