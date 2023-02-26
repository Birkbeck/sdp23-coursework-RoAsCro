package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A singleton factory class for creating any concrete class that extends the abstract Instruction class.
 * <p></p>
 * This class uses the XML file instructions.xml that lists the available Instructions. As long as an Instruction has
 * been implemented correctly and has been added to the instructions file, this class will be capable of instantiating
 * it via the getInstruction method.
 *
 * @author Roland Crompton
 */

public class InstructionFactory {

    /**
     * The list containing instances possible instructions constructed with default null values. These should not be
     * used for anything except registering Instructions in the classMap. Automatically loaded via Spring autowiring
     * when getInstance is called for the first time when running a program via autowiring.
     */
    private List<Instruction> instructions;

    /**
     * A map storing Instruction opcodes and the classes to which they correspond. Used for instantiating Instructions
     * from their opcodes.
     */
    private final Map<String, Class<? extends Instruction>> classMap = new HashMap<>();

    /**
     * A Null implementation of RegisterName for use in autowiring Instructions.
      */
    public static class NullRegisterName implements RegisterName {
        @Override
        public String name() {
            return " ";
        }
    }

    /**
     * A map containing the possible parameter types for Instructions and the corresponding functions for converting
     * Strings to those types.
     */
    private static final Map<Class<?>, Function<String, ?>> PARAM_TYPES = Map.of(
            String.class, x -> x,
            RegisterName.class, Registers.Register::valueOf,
            int.class, Integer::parseInt
            );

    /**
     * A comparator used to compare Instruction comparators. Constructors are ordered according to the number of String
     * parameters they have. This is to reduce the ambiguity of which constructor is intended to be called by the
     * program.
     */
    private static final Comparator<Constructor<?>> CONSTRUCTOR_COMPARATOR = (x, y) -> {
        int xValue = (int) Arrays.stream(x.getParameterTypes()).filter(x1 -> x1 == String.class).count();
        int yValue = (int) Arrays.stream(y.getParameterTypes()).filter(y1 -> y1 == String.class).count();
        return xValue - yValue;};

    /**
     * Sets the instructions. Should be only used once when first instantiating the InstructionFactory using autowiring.
     * @param instructions a list containing an instance of every Instruction in the XML file
     */
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    /**
     * Safely gets the Class of the instruction corresponding to the given opcode.
     * @param opcode the opcode of the Instruction Class to be returned
     * @return the class of the Instruction corresponding to the opcode. Null if the opcode does not belong to a valid
     * instruction
     */
    private Class<? extends Instruction> getInstructionClass(String opcode) {
        if (!classMap.containsKey(opcode))
            return null;
        return classMap.get(opcode);
    }

    /**
     * Creates a new instance of the Instruction corresponding to the given opcode, constructed with the label and set
     * of parameters. Attempts to find a constructor for the given Instruction that matches the set of parameters.
     * <p></p>
     * Will return null if: The opcode or any of the parameters are null; The opcode does not match the
     * opcode of a valid Instruction; The number of parameters does not match a constructor of the given Instruction
     * type; One or more of the parameters cannot be cast to a set of
     * types corresponding to the parameters of any constructor of the Instruction type.
     * <p></p>
     * In these cases, an appropriate error message will be displayed.
     *
     * @param label the label of the instruction (can be null)
     * @param opcode the opcode of the instruction
     * @param params the set of parameters for the instruction
     * @return an appropriate Instruction constructed from the parameters and label. Null if the factory was unable to
     * construct the Instruction for any reason
     */
    public Instruction getInstruction(String label, String opcode, List<String> params) {
        Class<? extends Instruction> classus = getInstructionClass(opcode);

        String errorMessage = "Error with instruction: " + ((label != null) ? label + " : " : "") + opcode + " "  +
                String.join(" ", params) + "\n";

        if (classus == null) {
            System.err.println(errorMessage + "No such instruction found. Available instructions:\n" + this);
            return null;
        }
        //Get constructors
        Constructor<?>[] constructors = classus.getConstructors();
        //Find only those constructors that match the given number of parameters and order the constructors such that
        // those with fewer String constructors will be checked first
        List<Constructor<?>> constructorList = Arrays.stream(constructors)
                .filter(c -> (c.getParameterCount() == params.size()+1))
                .sorted(CONSTRUCTOR_COMPARATOR)
                .toList();

        if (constructorList.size() != 0 && !params.contains(null)) {
            //Attempt to match the given parameters with the types of the parameters of the remaining constructors
            List<Instruction> candidateInstructions =
                    constructorList.stream()
                            .map(c -> {
                                //The first parameter is skipped as that is the label
                                Iterator<Class<?>> typeIter = Arrays.stream(c.getParameterTypes()).skip(1).iterator();
                                LinkedList<Object> typedParams = new LinkedList<>(params.stream()
                                        .map(p -> castString(p, typeIter.next())).toList());
                                if (!typedParams.contains(null)) {
                                    try {
                                        typedParams.push(label);
                                        return (Instruction) c.newInstance(typedParams.toArray());

                                    } catch (InstantiationException | InvocationTargetException |
                                             IllegalAccessException e) {
                                        typedParams.clear();
                                    }
                                }
                                return null;
                            }).filter(Objects::nonNull).toList();

            if (candidateInstructions.size() > 0) {
                return candidateInstructions.get(0);
            }
        }
        System.err.println(buildErrorMessage(errorMessage, constructors, params));
        return null;
    }

    /**
     * Attempts to convert the given String into the given type. Type should only be String, RegisterName, or int.
     * @param input the string to be converted
     * @param type the type the input is to be converted to. Must be String, RegisterName, or int
     * @return the converted input if it's possible to convert it, null otherwise
     */
    private Object castString(String input, Class<?> type) {
        try {
            return PARAM_TYPES.get(type).apply(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * A method for creating an error message for when the parameters are do not match any constructor for a requested
     * Instruction. This will be in the format:
     * <p></p>
     * "Error with instruction: label: opcode parameters
     * <p></p>
     * Possible sets of valid parameters for this instruction type:
     * <p></p>
     * A list detailing the possible constructors
     * <p></p>
     * Got: a list of the parameters given.
     * "
     * @param errorMessage a string containing the line in the program the instruction came from
     * @param constructors a set of constructors for the requested Instruction
     * @param params the set of parameters given
     * @return the error message
     */
    private String buildErrorMessage(String errorMessage, Constructor<?>[] constructors, List<String> params) {
        StringBuilder paramsErrorMessage = new StringBuilder(errorMessage +
                "Possible sets of valid parameters for this instruction type:");
        Arrays.stream(constructors).forEach( constructor ->
                paramsErrorMessage.append("\n").append(constructor.getParameterCount() - 1).append(" parameters: ")
                        .append(Arrays.stream(constructor.getParameterTypes())
                        .skip(1)
                        .map(Class::getName)
                        .collect(Collectors.joining(", "))));
        paramsErrorMessage.append("\nGot: ").append(params.size()).append(" parameters: ").append(String.join(", ", params));
        return paramsErrorMessage.toString();
    }


    /**
     * Private construction method allowing this class to be a singleton that can only be instantiated via getInstance
     */
    private InstructionFactory() {}

    /**
     * The proper method of instantiating this class.
     * @return the instance of InstructionFactory listed in instructions.xml and autowired with the Instructions listed
     * there
     */
    public static InstructionFactory getInstance() {
        InstructionFactory factory = (InstructionFactory) new ClassPathXmlApplicationContext("instructions.xml").getBean("insFactory");
        factory.instructions.forEach(i -> factory.classMap.put(i.getOpcode(), i.getClass()));
        return factory;
    }

    /**
     * Overridden toString method. Gives a list of the opcodes of all Instructions loaded into the factory at
     * construction in alphabetical order.
     * @return a String containing a list of Instructions this factory can create
     */
    @Override
    public String toString() {
        return String.join(", ", classMap.keySet().stream().sorted().toList());
    }



}
