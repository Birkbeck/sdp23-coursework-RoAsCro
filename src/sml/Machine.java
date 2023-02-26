package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author ...
 * @author Roland Crompton
 */
public final class Machine {

	private final Labels labels = new Labels();

	private final List<Instruction> program = new ArrayList<>();

	private final Registers registers;

	// The program counter; it contains the index (in program)
	// of the next instruction to be executed.
	private int programCounter = 0;

	public Machine(Registers registers) {
		this.registers = registers;
	}

	/**
	 * Execute the program in program, beginning at instruction 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() {
		programCounter = 0;
		registers.clear();
		while (programCounter < program.size()) {
			Instruction ins = program.get(programCounter);
			int programCounterUpdate = ins.execute(this);
			programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
				? programCounter + 1
				: programCounterUpdate;
		}
	}

	public Labels getLabels() {
		return this.labels;
	}

	public List<Instruction> getProgram() {
		return this.program;
	}

	public Registers getRegisters() {
		return this.registers;
	}


	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Checks if two Machines are equal. Two machines are equal if: Their labels are the same; their program is the same
	 * ;the values of their registers are the same; their program counters are at the same point in the program.
	 *
	 * @param o another object against which this Machine will be tested
	 * @return false if o is not a Machine or is not equal to this Machine. True otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Machine other) {
			return Objects.equals(this.labels, other.labels)
					&& Objects.equals(this.program, other.program)
					&& Objects.equals(this.registers, other.registers)
					&& this.programCounter == other.programCounter;
		}
		return false;
	}

	/**
	 * Returns a hash code for this Machine. The hashcode is based on the Machine's labels, program, the values of its
	 * registers, and the position of its programCounter.
	 *
	 * @return a hash code for this machine
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
