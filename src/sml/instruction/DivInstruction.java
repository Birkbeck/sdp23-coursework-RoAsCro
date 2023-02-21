package sml.instruction;

import sml.Machine;
import sml.RegisterName;

public class DivInstruction extends BiRegisterInstruction {

    public static final String OP_CODE = "div";


    /**
     * Constructor: takes an optional label, opcode, and two RegisterNames corresponding to registers in some machine.
     *
     * @param label  optional label (can be null)
     * @param result the not null RegisterName of the register storing the first operand and where the result will be stored
     * @param source the not null RegisterName of the register storing the second operand.
     */
    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Divides the value stored in the register in m corresponding to result field by the value stored in the register
     * corresponding to the source field. The result is stored in the result register.
     * If the value of divisor is 0, the values stored in both registers will remain unchanged, an error message will be
     * printed,and the normal program counter will be returned.
     *
     * @param m the machine the instruction runs on
     * @return the normal program counter update of -1.
     */
    @Override
    public int execute(Machine m) {
        if (m.getRegisters().get(source) == 0) {
            System.out.println("Error"+
                    " in instruction "
                    + this
                    + ": Cannot divide by zero");
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        }
        return super.execute(m, (one, two) -> one/two);
    }
}
