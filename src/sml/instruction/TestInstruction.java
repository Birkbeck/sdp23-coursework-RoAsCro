package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import static sml.Registers.Register.EAX;

public class TestInstruction extends Instruction {

    private final RegisterName source;

    private final String targetLabel;

    private final int x;

    public static final String OP_CODE = "tes";

    public static final String DEFAULT_TARGET_LABEL = "a";

    public static final RegisterName DEFAULT_SOURCE = EAX;

    public static final int DEFAULT_X = 1;

    public TestInstruction(String label) {
        super(label, OP_CODE);
        this.source = DEFAULT_SOURCE;
        this.targetLabel = DEFAULT_TARGET_LABEL;
        this.x = DEFAULT_X;
    }

    public TestInstruction(String label, String targetLabel, RegisterName source, int x) {
        super(label, OP_CODE);
        this.x = x;
        this.source = source;
        this.targetLabel = targetLabel;
    }

    public TestInstruction(String label, String targetLabel, RegisterName source) {
        super(label, OP_CODE);
        this.x = DEFAULT_X;
        this.source = source;
        this.targetLabel = targetLabel;
    }

    public TestInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.x = DEFAULT_X;
        this.source = source;
        this.targetLabel = DEFAULT_TARGET_LABEL;
    }

    public TestInstruction(String label, int x) {
        super(label, OP_CODE);
        this.x = x;
        this.source = DEFAULT_SOURCE;
        this.targetLabel = DEFAULT_TARGET_LABEL;
    }

    @Override
    public int execute(Machine m) {
        targetLabel.length();
        source.name();
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + x + " " + source + " " + targetLabel;
    }
}
