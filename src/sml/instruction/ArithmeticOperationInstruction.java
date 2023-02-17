package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.function.BinaryOperator;

public abstract class ArithmeticOperationInstruction extends Instruction {

    protected final RegisterName result;

    protected final RegisterName source;

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     */
    public ArithmeticOperationInstruction(String label, String opcode, RegisterName result, RegisterName source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    public void operation(Machine m, BinaryOperator<Integer> f) {
//        int value1 = m.getRegisters().get(result);
//        int value2 = m.getRegisters().get(source);
//        m.getRegisters().set(result, value1 + value2);
    }

}
