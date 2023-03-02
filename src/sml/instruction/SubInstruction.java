package sml.instruction;

import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

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
    private final static String OP_CODE = "sub";

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
     * Checks if two SubInstructions are equal. Two SubInstructions are equal if they have the same label, result, and
     * source.
     *
     * @param o an object to be compared to this SubInstruction.
     * @return false if o is not an SubInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SubInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.result == instruction.result &&
                    this.source == instruction.source;
        }
        return false;
    }

    /**
     * Executes the instruction on the given machine m. Takes the value stored in the Register in m corresponding to
     * RegisterName source defined at construction, subtracts it from the value stored in the Register in m
     * corresponding to RegisterName result defined a construction, and stores the result in the result Register in m.
     * <p></p>
     * The program counter in m will then move onto the instruction with the next address
     *
     * @param m the machine the instruction runs on, where the values will be retrieved from and where the result will
     *          be stored
     * @return the normal program counter update indicating the program counter should move onto the instruction with
     * the next address
     */
    @Override
    public int execute(Machine m) {
        return super.execute(m, (one, two) -> one - two);
    }

}
