package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class JnzInstruction extends Instruction {

    public static final String OP_CODE = "jnz";

    private RegisterName reg;

    private String targetLabel;
    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     *
     */
    public JnzInstruction(String label, RegisterName reg, String targetLabel) {
        super(label, OP_CODE);
        this.reg = reg;
        this.targetLabel = targetLabel;
    }

    @Override
    public int execute(Machine m) {
        int programCounterUpdate;
        if (m.getRegisters().get(reg) == 0)
            programCounterUpdate = NORMAL_PROGRAM_COUNTER_UPDATE;
        else
            programCounterUpdate = m.getLabels().getAddress(targetLabel);
        return programCounterUpdate;
    }

    @Override
    public String toString() {
        return null;
    }
}
