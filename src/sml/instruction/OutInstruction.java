package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * A concrete implementation of the abstract Instruction class. An instruction for printing an instruction on the
 * console.
 * <p></p>
 * This instruction takes a RegisterName source.
 * When executed, the value stored in source will be printed on the console.
 *
 * @author Roland Crompton
 */

public class OutInstruction extends Instruction {

    /**
     * The RegisterName for a register whose value will be used in the execution. Should never be null.
     */
    private final RegisterName source;

    /**
     * The operation code for all OutInstructions. The name of the operation.
     */
    public static final String OP_CODE = "out";

    /**
     *  Constructs a new OutInstruction with an optional label, and a RegisterName source.
     *
     * @param label optional label (can be null)
     * @param source the not-null RegisterName of the register to be printed
     */
    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    /**
     * Prints on the console the current value of the register in Machine m corresponding to the RegisterName source
     * given at construction.
     *
     * @param m the not null machine the instruction runs on
     * @return the normal program counter update indicating the program counter should move onto the instruction with
     * the next address
     */
    @Override
    public int execute(Machine m) {
        System.out.println(m.getRegisters().get(source));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Returns a String representation of this OutInstruction. This will be in the format "[label: ]out result source",
     * where label, result, and source are the fields defined at construction, and the text enclosed in the
     * square brackets is optional.
     *
     * @return a String representation of this OutInstruction readable by humans.
     */
    @Override
    public String toString() {
        return getLabelString() + OP_CODE + " " + source;
    }

    /**
     * Checks if two OutInstructions are equal. Two OutInstructions are equal if they have the same label and
     * source.
     *
     * @param o an object to be compared to this OutInstruction.
     * @return false if o is not an MovInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof OutInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.source == instruction.source;
        }
        return false;
    }

    /**
     * Returns a hash code for this OutInstruction. If two OutInstructions have the same opcode, label, and source they will
     * have the same hash code.
     *
     * @return a hash code representing this OutInstruction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(opcode, label, source);
    }
}
