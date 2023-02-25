package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

// TODO: write a JavaDoc for the class

/**
 * Stores a Map containing the labels of instructions in a program and the addresses of those instructions.
 * Allows for adding and looking up the address of labels.
 *
 * @author ...
 * @author Roland Crompton
 */
public final class Labels {

	/**
	 * The hashmap in which is stored a String representing an Instruction's label, and an integer representing the Instruction's
	 * location in the program.
	 */
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 * <p></p>
	 * If the label already exists in the program, the label will not be added to the map, and an error message will be
	 * displayed.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 * @return false if the label is not already in use. True otherwise
	 */
	public boolean addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)) {
			System.out.println("Error at line " +
					address +
					" of program. Label "
					+ label
					+ " is already in use.");
			return false;
		} else {
			labels.put(label, address);
			return true;
		}
	}

	/**
	 * Returns the address associated with the label.
	 * <p></p>
	 * If the label does not exist, displays an error message and returns the normal program counter update imported
	 * from class Instruction.
	 *
	 * @param label the label
	 * @return the address the label refers to if the label exists. Otherwise, the normal program counter update
	 */
	public int getAddress(String label) {
		// TODO: Where can NullPointerException be thrown here?
		//       (Write an explanation.)
		// A null pointer exception can be thrown if the label does not correspond to a key in the HashMap, as the
		// program will be trying to use the value of a reference that has not been assigned.
		
		//       Add code to deal with non-existent labels.
		if (!labels.containsKey(label)) {
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
		return labels.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.map(l -> l.getKey() + " -> " + l.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
	}


	/**
	 * Checks whether an instance of Labels is equal to this instance of Labels. Two instances of class Labels are the
	 * same if they have identical elements in their hash tables.
	 *
	 * @param o the Object to be compared to this Labels
	 * @return false if o is not an instance of Labels, or it's HashMap contains different elements. True otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels labels2) {
			return labels.equals(labels2.labels);
		}
		return false;
	}

	/**
	 * Returns a hash code for this instance of Labels. Two Labels will have the same hash code if they have the same
	 * elements in their HashMaps.
	 *
	 * @return the hash code for this instance of Labels
	 */
	@Override
	public int hashCode() {
		return labels.hashCode();
	}
	// TODO: Implement equals and hashCode (needed in class Machine).

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
