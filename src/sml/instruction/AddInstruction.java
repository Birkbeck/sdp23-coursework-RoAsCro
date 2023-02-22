package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * A concrete implementation of the abstract Instruction class. An instruction for adding two values.
 * <p></p>
 * The instruction takes two RegisterNames, result and source, at construction.
 * When executed will add the values stored at the two registers, storing the resulting value in result.
 *
 * @author ...
 */

public class AddInstruction extends Instruction {
	/**
	 * The RegisterName for a register whose value will be used in the execution, and where the result will be stored.
	 * Should never be null.
	 */
	private final RegisterName result;
	/**
	 * The RegisterName for a register whose value will be used in the execution. Should never be null.
	 */
	private final RegisterName source;

	/**
	 * The operation code for all AddInstructions. The name of the operation.
	 */
	public static final String OP_CODE = "add";

	/**
	 * Constructs a new AddInstruction with an optional label, a RegisterName result, and a RegisterName source.
	 *
	 * @param label optional label for the instruction (can be null)
	 * @param result a not null RegisterName corresponding to a Register in some machine. Value stored there will be
	 *               added to source on execution, and the resulting value will then be stored in result
	 * @param source a not null RegisterName corresponding to a Register in some machine. Value stored there will be
	 *               added to result on execution
	 */
	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Executes the instruction on the given machine m. Takes the value stored in the Register in m corresponding to
	 * RegisterName result defined at construction, adds it to the value stored in the Register in m corresponding to
	 * RegisterName source defined a construction, and stores the result in the result Register in m.
	 * <p></p>
	 * The program counter in m will then move onto the instruction with the next address.
	 *
	 * @param m the machine the instruction runs on, where the values will be retrieved from and where the result will
	 *          be stored
	 * @return the normal program counter update indicating the program counter should move onto the instruction with
	 * the next address
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 + value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	/**
	 * Returns a String representation of this AdInstruction. This will be in the format "[label: ]add result source",
	 * where label, result, and source are the fields defined at construction, and the text enclosed in the square
	 * brackets is optional.
	 *
	 * @return a String representation of this AddInstruction readable by humans.
	 */
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	/**
	 * Checks if two AddInstructions are equal. Two AddInstructions are equal if they have the same label, result, and
	 * source.
	 *
	 * @param o an object to be compared to this AddInstruction.
	 * @return false if o is not an AddInstruction or is not equal to this. True if o is equal to this.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof AddInstruction instruction) {
			return Objects.equals(this.label, instruction.label) &&
					this.result == instruction.result &&
					this.source == instruction.source;
		}
		return false;
	}
	
}
