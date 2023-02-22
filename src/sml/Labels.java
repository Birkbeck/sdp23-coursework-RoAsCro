package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	private static final int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Adds a label with the associated address to the map.
	 * <p></p>
	 * If the label already exists in the program, the label will not be added to the map, and an error message will be
	 * displayed.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)) {
			System.out.println("Error at line " +
					address +
					" of program. Label "
					+ label
					+ " is already in use. Label not assigned.");
		} else
			labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 * <p></p>
	 * If the label does not exist, displays an error message and returns the normal program counter update, indicating
	 * the program counter should move onto the instruction with the next address.
	 * @param label the label
	 * @return the address the label refers to if the label exists. Otherwise, the normal program counter update
	 */
	public int getAddress(String label) {
		// TODO: Where can NullPointerException be thrown here?
		//       (Write an explanation.)
		//A null pointer exception can be thrown if the label does not correspond to a key in the HashMap.
		//       Add code to deal with non-existent labels.
		if (!labels.containsKey(label)) {
			System.out.println("Error: Label " +
					label +
					" is not assigned. Program counter moving onto the next address.");
			return NORMAL_PROGRAM_COUNTER_UPDATE;
		}
		return labels.get(label);
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		// TODO: Implement the method using the Stream API (see also class Registers).
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(l -> l.getKey() + " -> " + l.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
	}


	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels labels2) {
			return labels.equals(labels2.labels);
		}
		return false;
	}
	// TODO: Implement equals and hashCode (needed in class Machine).

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
