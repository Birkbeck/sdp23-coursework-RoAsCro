package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * A concrete implementation of the abstract Instruction class. An instruction for storing integer values in a machine's
 * registers.
 * <p></p>
 * This instruction takes a RegisterName result and an integer x.
 * When executed, x will be stored in the Register corresponding to result.
 *
 * @author Roland Crompton
 */

public class MovInstruction extends Instruction {

    /**
     * The operation code for all MovInstructions. The name of the operation.
     */
    private static final String OP_CODE = "mov";

    /**
     * An integer value to be stored in a register upon execution.
     */
    private final int value;

    /**
     * The RegisterName for the register where the given integer will be stored. Should never be null.
     */
    private final RegisterName result;

    /**
     * Constructs a new AddInstruction with an optional label, a RegisterName result, and an integer value.
     *
     * @param label  optional label (can be null)
     * @param result the not-null RegisterName of the register where integer value will be stored
     * @param value the integer value to be stored in the register
     */
    public MovInstruction(String label, RegisterName result, int value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    /**
     * Checks if two MovInstructions are equal. Two MovInstructions are equal if they have the same label, result, and
     * value.
     *
     * @param o an object to be compared to this MovInstruction.
     * @return false if o is not an MovInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MovInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.value == instruction.value &&
                    this.result == instruction.result;
        }
        return false;
    }

    /**
     * Stores the integer value given at construction in the register in Machine m corresponding to the RegisterName
     * result given at construction.
     *
     * @param m the machine the instruction runs on
     * @return the normal program counter update indicating the program counter should move onto the instruction with
     * the next address
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Returns a hash code for this MovInstruction. If two MovInstructions have the same opcode, label, result, and
     * value, they will have the same hash code.
     *
     * @return a hash code representing this MovInstruction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(opcode, label, result, value);
    }

    /**
     * Returns a String representation of this MovInstruction. This will be in the format "[label: ]mov result source",
     * where label, result, and source are the fields defined at construction, and the text enclosed in the
     * square brackets is optional.
     *
     * @return a String representation of this MovInstruction readable by humans.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result.toString() + " " + value;
    }

}
