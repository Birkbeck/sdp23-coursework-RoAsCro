package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * A concrete implementation of the abstract Instruction class. An instruction for jumping to other instructions in the
 * program.
 * <p></p>
 * When executed, checks whether the register specified at construction is zero. If not, instructs the machine to jump
 * to the instruction in the program with the label specified at construction. Otherwise, instructs the machine to move
 * on to the next instruction sequentially.
 *
 */

public class JnzInstruction extends Instruction {

    /**
     * The RegisterName for the register whose value will be checked in the execution. Should never be null.
     */
    private final RegisterName source;

    /**
     * A String corresponding to the label of another instruction in the program.
     */
    private final String targetLabel;

    /**
     * The operation code for all JnzInstructions. The name of the operation.
     */
    public static final String OP_CODE = "jnz";

    /**
     * Constructs a new JnzInstruction with an optional label, a RegisterName source, and a String targetLabel.
     *
     * @param label optional label (Can be null)
     * @param source the not-null RegisterName of the register to be checked.
     * @param targetLabel the not-null String corresponding to a label of another instruction in the program.
     */
    public JnzInstruction(String label, RegisterName source, String targetLabel) {
        super(label, OP_CODE);
        this.source = source;
        this.targetLabel = targetLabel;
    }

    /**
     * If the value stored in the register corresponding to RegisterName source specified at construction is not equal
     * to 0, the instruction will tell the program counter in Machine m to jump to the instruction with the label given
     * at construction. Otherwise, the program continues to the instruction with the next address.
     *
     * @param m the machine the instruction runs on
     * @return the integer corresponding to the place in the program of the instruction to be jumper to if the value of
     * the register is not 0, otherwise returns the normal program counter update indicating the program counter should
     * move onto the instruction with the next address
     */
    @Override
    public int execute(Machine m) {
        int programCounterUpdate;
        if (m.getRegisters().get(source) == 0)
            programCounterUpdate = NORMAL_PROGRAM_COUNTER_UPDATE;
        else
            programCounterUpdate = m.getLabels().getAddress(targetLabel);
        return programCounterUpdate;
    }

    /**
     * Returns a String representation of this JnzInstruction. This will be in the format "[label: ]jnz result source",
     * where label, result, and source are the fields defined at construction, and the text enclosed in the
     * square brackets is optional.
     *
     * @return a String representation of this JnzInstruction readable by humans.
     */
    @Override
    public String toString() {
        return getLabelString() + OP_CODE + " " + source + " " + targetLabel;
    }

    /**
     * Checks if two JnzInstructions are equal. Two JnzInstructions are equal if they have the same label,
     * source, and targetLabel.
     *
     * @param o an object to be compared to this JnzInstruction.
     * @return false if o is not an JnzInstruction or is not equal to this. True if o is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof JnzInstruction instruction) {
            return Objects.equals(this.label, instruction.label) &&
                    this.targetLabel.equals(instruction.targetLabel) &&
                    this.source == instruction.source;
        }
        return false;
    }

    /**
     * Returns a hash code for this JnzInstruction. If two JnzInstructions have the same opcode, label, targetLabel, and
     * source, they will have the same hash code.
     *
     * @return a hash code representing this JnzInstruction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(opcode, label, targetLabel, source);
    }
}
