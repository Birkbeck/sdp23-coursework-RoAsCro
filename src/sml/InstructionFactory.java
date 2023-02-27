package sml;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;

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

    private static InstructionFactory instance;
    private static boolean correctFormatting;

    private static final LinkedList<String> PARAMETERS = new LinkedList<>();

    private static final ClassPathXmlApplicationContext BEAN_FACTORY = new ClassPathXmlApplicationContext("instructions.xml");

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


    private static Object getObject(Class<?> targetClass) {
        if (!PARAMETERS.isEmpty()) {
            try {
                return PARAM_TYPES.get(targetClass).apply(PARAMETERS.pop());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    public static String getString() {
        Object o = getObject(String.class);
        if (o instanceof String s)
            return s;
        correctFormatting = false;
        return "";
    }

    public static RegisterName getRegisterName() {
        Object o = getObject(RegisterName.class);
        if (o instanceof RegisterName r)
            return r;
        correctFormatting = false;
        return new NullRegisterName();
    }

    public static int getInt() {
        Object o = getObject(int.class);
        if (o instanceof Integer i)
            return i;
        correctFormatting = false;
        return 0;
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
        PARAMETERS.clear();
        String errorMessage = "Error with instruction: " + ((label != null) ? label + " : " : "") + opcode + " "  +
                String.join(" ", params) + "\n";
        if (opcode == null || params.contains(null)) {
            return null;
        }
        try {
            PARAMETERS.add(Objects.requireNonNullElse(label, ""));
            PARAMETERS.addAll(params);
            correctFormatting = true;
            Instruction returnInstruction = (Instruction) BEAN_FACTORY.getBean(opcode);

            if (correctFormatting && PARAMETERS.isEmpty()) {
                    System.out.println(returnInstruction);
                    return returnInstruction;
                }
            System.err.println(errorMessage
                    + buildErrorMessage(returnInstruction.getClass().getConstructors(), params));
            return null;
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println(errorMessage + "No instruction of that name found.");
            return null;
        }
    }



    /**
     * A method for creating an error message for when the parameters are do not match any constructor for a requested
     * Instruction.
     *
     * @param constructors a set of constructors for the requested Instruction
     * @param params the set of parameters given
     * @return the error message
     */
    private String buildErrorMessage(Constructor<?>[] constructors, List<String> params) {
        StringBuilder paramsErrorMessage = new StringBuilder(
                "Valid parameters for this instruction type:");
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
        if (instance == null)
            return new InstructionFactory();
        return instance;
    }

    /**
     * Overridden toString method. Gives a list of the opcodes of all Instructions loaded into the factory at
     * construction in alphabetical order.
     * @return a String containing a list of Instructions this factory can create
     */
    @Override
    public String toString() {
        return String.join(", ", Arrays.stream(BEAN_FACTORY.getBeanNamesForType(Instruction.class))
                .sorted().toList());
    }



}
