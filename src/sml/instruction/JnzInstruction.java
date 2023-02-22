package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

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
    private RegisterName source;

    /**
     * A String corresponding to the label of another instruction in the program.
     */
    private String targetLabel;

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
     * at construction. Otherwise, the program continues to the next instruction sequentially.
     *
     * @param m the machine the instruction runs on
     * @return the integer corresponding to the place in the program of the instruction to be jumper to if the value of
     * the register is not 0, otherwise returns the normal program counter update indicating the program counter should
     * move onto the next instruction sequentially
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
}
