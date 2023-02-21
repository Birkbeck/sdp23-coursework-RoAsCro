package sml.instruction;

import sml.Machine;
import sml.RegisterName;

public class DivInstruction extends ArithmeticOperationInstruction{



    /**
     * Constructor: takes an optional label, opcode, and two RegisterNames corresponding to registers in some machine.
     *
     * @param label  optional label (can be null)
     * @param result the not null RegisterName of the register storing the first operand and where the result will be stored
     * @param source the not null RegisterName of the register storing the second operand.
     */
    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, opcode, result, source);
    }

    @Override
    public int execute(Machine machine) {
        return 0;
    }
}
