package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class JnzInstruction extends Instruction {

    public static final String OP_CODE = "jnz";

    private RegisterName reg;

    private String targetLabel;

    /**
     *
     *
     * @param label optional label. Can be null.
     * @param reg the not-null RegisterName of the register to be checked.
     * @param targetLabel the not-null String corresponding to a label of another instruction in the program.
     */
    public JnzInstruction(String label, RegisterName reg, String targetLabel) {
        super(label, OP_CODE);
        this.reg = reg;
        this.targetLabel = targetLabel;
    }

    /**
     * If the value stored in the register corresponding to the RegisterName specified at construction does not equal 0,
     * will jump to the instruction with the label given at construction. Otherwise, the program continues to the next
     * instruction.
     *
     * @param m the machine the instruction runs on
     * @return the integer corresponding to the place in the program of the target instruction if the value of the
     * register is not 0, other the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        int programCounterUpdate;
        if (m.getRegisters().get(reg) == 0)
            programCounterUpdate = NORMAL_PROGRAM_COUNTER_UPDATE;
        else
            programCounterUpdate = m.getLabels().getAddress(targetLabel);
        return programCounterUpdate;
    }

    /**
     * Overridden toString method.
     *
     * @return a string in the format "l: o r t" where l is the label if there is one, o is the opcode, r is
     * the register specified at construction, and t is the target label specified at construction.
     */
    @Override
    public String toString() {
        return getLabelString() + OP_CODE + " " + reg + " " + targetLabel;
    }
}
