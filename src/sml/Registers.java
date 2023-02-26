package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Registers of a Machine, storing their RegisterNames and the corresponding integer values.
 * <p></p>
 * Registers are predefined by the enum Register. The value of registers can be set, gotten, and cleared.
 * @author ...
 * @author Roland Crompton
 */
public final class Registers {

    /**
     * The HashMap in which the Registers and their corresponding integer values are stored.
     */
    private final Map<Register, Integer> registers = new HashMap<>();

    /**
     * Implementation of interface RegisterName.
     */
    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    /**
     * Constructs a new instance of Registers with the value of all Registers set to zero.
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Sets the value of all Registers to zero.
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     *Compares two instances of Registers. Two instances of Registers are equal if their Registers are all set to the
     * same values.
     *
     * @param o the object to be compared to this instance of Registers
     * @return false if o is not an instance of Registers or if the values of o's Registers are not set to the same
     * values as this instance's Registers.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    /**
     * Returns a hash code for this instance of Registers. Two instances of Registers will have the same hash code if
     * their Registers are all set to the same values.
     *
     * @return the hash code for this instance of Registers
     */
    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    /**
     * Returns a String representation of this instance of Registers in the format "[EAX = x, EBX = y, ... , EDI = z]"
     * where x, y, and z are the integer values of their respective Registers.
     *
     * @return a String value representing this instance of Registers readable by humans.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
