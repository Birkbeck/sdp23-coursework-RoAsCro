package sml.instruction;

import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * A concrete implementation of the abstract BiRegisterInstruction class. An instruction for dividing one value by
 * another.
 * <p></p>
 * The instruction takes two RegisterNames, result and source, at construction.
 * When executed will divide the value stored at result by the value stored at source, storing the resulting value in
 * result.
 *
 * @author Roland Crompton
 */
public class DivInstruction extends BiRegisterInstruction {

    /**
     * The operation code for all DivInstructions. The name of the operation.
     */
    public static final String OP_CODE = "div";


    /**
     * Constructs a new DivInstruction with an optional label, a RegisterName result, and a RegisterName source.
     *
     * @param label optional label for the instruction (can be null)
     * @param result a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               divided by source on execution, and the resulting value will then be stored in result
     * @param source a not null RegisterName corresponding to a Register in some machine. Value stored there will be
     *               the divisor to result on execution
     */
    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Divides the value stored in the register in Machine m corresponding to result field by the value stored in the
     * register corresponding to the source field. The result is stored in the result register.
     * <p></p>
     * The program counter in m will then move onto the next instruction sequentially.
     * <p></p>
     * If the value of divisor is 0, the values stored in both registers will remain unchanged,and an error message will
     * be printed. The program counter in m will then move onto the instruction with the next address
     *
     * @param m the machine the instruction runs on, where the values will be retrieved from and where the result will
     *          be stored
     * @return the normal program counter update indicating the program counter should move onto the instruction with
     * the next address
     */
    @Override
    public int execute(Machine m) {
        if (m.getRegisters().get(source) == 0) {
            System.out.println("Error"+
                    " in instruction "
                    + this
                    + ": Cannot divide by zero");
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        }
        return super.execute(m, (one, two) -> one/two);
    }

    /**
     * Checks if two DivInstructions are equal. Two DivInstructions are equal if they have the same label, result, and
     * source.
     *
     * @param o an object to be compared to this DivInstruction.
     * @return false if o is not an DivInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof DivInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.result == instruction.result &&
                    this.source == instruction.source;
        }
        return false;
    }
}
