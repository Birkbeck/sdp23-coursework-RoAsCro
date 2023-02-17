package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.function.BinaryOperator;

/**
 * Abstract class for implementing Instructions that perform an arithmetic operation on two registers r and s, storing
 * the result in the second register. The operation will execute in the format "r operator s".
 *
 * @author Roland Crompton
 */
public abstract class ArithmeticOperationInstruction extends Instruction {

    protected final RegisterName result;

    protected final RegisterName source;

    /**
     * Constructor: takes an optional label, opcode, and two RegisterNames corresponding to registers in some machine.
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     * @param result the RegisterName of the register storing the first operand and where the result will be stored
     * @param source the RegisterName of the register storing the second operand.
     */
    public ArithmeticOperationInstruction(String label, String opcode, RegisterName result, RegisterName source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the given BinaryOperator on the registers specified at construction in a Machine.
     *
     * @param m a machine whose registers will ahve the operation performed on them.
     * @param f a BinaryOperator specifying the operation to be done to the registers.
     * @return  the normal program counter update of -1.
     */
    public int execute(Machine m, BinaryOperator<Integer> f) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, f.apply(value1, value2));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Overridden toString method.
     *
     * @return a string in the format "l: o r s" where l is the label if there is one, o is the opcode, and r and s are
     * the registers specified at construction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

}
