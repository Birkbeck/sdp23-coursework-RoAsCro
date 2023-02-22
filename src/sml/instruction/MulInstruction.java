package sml.instruction;

import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * Concrete implementation of the abstract BiRegisterInstruction class. An instruction for multiplying two values.
 * <p></p>
 * This instruction takes two register names, result and source, at construction.
 * When executed, the value stored at result will be multiplied by the value store at source, and the result stored in
 * result.
 *
 * @author Roland Crompton
 */
public class MulInstruction extends BiRegisterInstruction {

    /**
     * The operation code for all MulInstructions. The name of the operation.
     */
    public static final String OP_CODE = "mul";

    /**
     * Constructs a new MulInstruction with an optional label, a RegisterName result, and a RegisterName source.
     *
     * @param label optional label for the instruction (can be null)
     * @param result a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               multiplied by source on execution, and the resulting value will then be stored in result
     * @param source a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               multiplied by result on execution
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Executes the instruction on the given machine m. Takes the value stored in the Register in m corresponding to
     * RegisterName result defined at construction, multiplies it by the value stored in the Register in m corresponding
     * to RegisterName source defined a construction, and stores the result in the result Register in m.
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
        return super.execute(m, (one, two) -> one * two);
    }

    /**
     * Checks if two MulInstructions are equal. Two MulInstructions are equal if they have the same label, result, and
     * source.
     *
     * @param o an object to be compared to this MulInstruction.
     * @return false if o is not an MulInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MulInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.result == instruction.result &&
                    this.source == instruction.source;
        }
        return false;
    }
}
