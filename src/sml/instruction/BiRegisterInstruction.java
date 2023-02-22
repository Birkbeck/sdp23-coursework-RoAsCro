package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * Abstract class for implementing Instructions that upon execution perform an operation on two registers r and s,
 * storing the result in r.
 *
 * @author Roland Crompton
 */
public abstract class BiRegisterInstruction extends Instruction {

    /**
     * The RegisterName for a register whose value will be used in the execution, and where the result will be stored.
     * Should never be null.
     */
    protected final RegisterName result;

    /**
     * The RegisterName for a register whose value will be used in the execution. Should never be null.
     */
    protected final RegisterName source;

    /**
     * Constructs a new BiRegisterInstruction with an optional label, an opcode, a RegisterName result, and a
     * RegisterName source.
     *
     * @param label  optional label (can be null)
     * @param opcode the not null operation name
     * @param result the not null RegisterName of the register storing the first operand and where the result will be
     *               stored
     * @param source the not null RegisterName of the register storing the second operand
     */
    public BiRegisterInstruction(String label, String opcode, RegisterName result, RegisterName source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the given BinaryOperator f on the value stored at the registers specified at construction in a Machine m.
     *
     * @param m the machine the instruction runs on, where the values will be retrieved from and where the result will
     *          be stored
     * @param f a BinaryOperator specifying the operation to be done to the values stored at the registers
     * @return the normal program counter update indicating the program counter should move onto the instruction with
     * the next address
     */
    public int execute(Machine m, BinaryOperator<Integer> f) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, f.apply(value1, value2));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Returns a String representation of this Instruction. This will be in the format "[label: ]opcode result source",
     * where label, opcode, result, and source are the fields defined at construction, and the text enclosed in the
     * square brackets is optional.
     *
     * @return a String representation of this Instruction readable by humans.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }



}
