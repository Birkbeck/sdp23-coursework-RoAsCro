package sml;

import java.util.List;
import java.util.Map;

public class InstructionFactory {

    private List<Instruction> instructions;

    private Map<String, Instruction> classMap;

    private void setInstructions(List<Instruction> list) {

    }

    private void setClassMap() {

    }

    public Class<? extends Instruction> getInstructionClass(String opcode) {
        return null;
    }

    public Instruction getInstruction(List<String> params) {
        return null;
    }

    private InstructionFactory() {}

    public static InstructionFactory getInstructionFactory() {
        return null;
    }



}
