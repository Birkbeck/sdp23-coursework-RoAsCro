package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A singleton factory class for creating any concrete class
 * extending the abstract Instruction class.
 * <p></p>
 * This class uses the XML file instructions.xml that lists the available Instructions.
 * As long as an Instruction has been implemented correctly and added to the instructions file,
 * this class will be capable of instantiating it via the getInstruction method.
 *
 * @author Roland Crompton
 */

public class InstructionFactory {

    /**
     * A flag for checking an instruction's parameters have been
     * formatted correctly when using getInstruction
     */
    private static boolean correctFormatting;

    /**
     * The factory for creating Instructions from beans
     */
    private static final ClassPathXmlApplicationContext BEAN_FACTORY =
            new ClassPathXmlApplicationContext("instructions.xml");

    /**
     * The instance of InstructionFactory for use by getInstance
     */
    private static InstructionFactory instance;

    /**
     * A linked list of parameters for the current attempt to construct an Instruction
     */
    private static final LinkedList<String> PARAMETERS = new LinkedList<>();

    /**
     * A map containing the possible parameter types for
     * Instructions and the corresponding functions for converting Strings to those types.
     */
    private static final Map<Class<?>, Function<String, ?>> PARAM_TYPES = Map.of(
            String.class, x->x,
            RegisterName.class, Registers.Register::valueOf,
            int.class, Integer::parseInt
    );

    /**
     * A Null Object implementation of RegisterName
     * for use in autowiring Instructions via getRegisterName
      */
    private static class NullRegisterName implements RegisterName {
        @Override
        public String name() {
            return " ";
        }
    }

    /**
     * Private construction method allowing this class to be
     * a singleton that can only be instantiated via getInstance
     */
    private InstructionFactory() {}

    /**
     * A method for creating an error message for when the parameters
     * do not match any constructor for a requested Instruction.
     *
     * @param constructors a set of constructors for the requested Instruction
     * @param params the set of parameters given
     * @return the error message
     */
    private String buildErrorMessage(Constructor<?>[] constructors, List<String> params) {
        StringBuilder paramsErrorMessage = new StringBuilder(
                "Valid parameters for this instruction type:\n");
        if (constructors.length != 0) {
            Constructor<?> constructor = constructors[0];
            paramsErrorMessage.append(constructor.getParameterCount()-1)
                    .append(" parameters: ")
                    .append(Arrays.stream(constructor.getParameterTypes())
                            // Skip the first parameter, as that is the label
                            .skip(1)
                            .map(Class::getName)
                            .collect(Collectors.joining(", ")));
        }
        paramsErrorMessage.append("\nGot: ")
                .append(params.size())
                .append(" parameters: ")
                .append(String.join(", ", params));
        return paramsErrorMessage.toString();
    }

    /**
     * The proper method of instantiating this class.
     * Returns the stored instance of InstructionFactory.
     * If there isn't one, creates one and stores it in instance.
     * @return an InstructionFactory
     */
    public static InstructionFactory getInstance() {
        if (instance == null)
            instance = new InstructionFactory();
        return instance;
    }

    /**
     * Returns an Object representing a String, int,
     * or RegisterName after attempting to convert the head element of
     * PARAMETERS into the targetClass. As PARAMETERS is a list of Strings,
     * it will always succeed in converting to String.
     * <p></p>
     * Should not be called directly, only through getString, getRegisterName, and getInt.
     * These are factory methods for autowiring Instructions using the contents of PARAMETERS.
     *
     * @param targetClass must be String.class, int.class, or RegisterName.class (can be null)
     * @return an object that's been converted to targetClass.
     * Null if PARAMETERS is empty or if the conversion failed
     */
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

    /**
     * Creates a new instance of the Instruction corresponding to the given opcode,
     * constructed with the label and set of parameters.
     * <p></p>
     * Stores the label and parameters in PARAMETERS, then attempts to instantiate the
     * Instruction through getBean. The bean will be autowired through the getInt, getRegisterName
     * and getString methods, emptying PARAMETERS.
     * <p></p>
     * Will return null if: The opcode or any of the parameters are null;
     * The opcode does not match the opcode of a valid Instruction;
     * The number of parameters does not match parameters of the given Instruction type;
     * One or more of the parameters cannot be cast to a set of
     * types corresponding to the parameters of the constructor of the Instruction type.
     * <p></p>
     * In these cases, an appropriate error message will be displayed.
     *
     * @param label the label of the instruction (can be null)
     * @param opcode the opcode of the instruction
     * @param params the set of parameters for the instruction
     * @return an appropriate Instruction constructed from the parameters and label.
     * Null if the factory was unable to construct the Instruction for any reason
     */
    public Instruction getInstruction(String label, String opcode, List<String> params) {
        PARAMETERS.clear();
        String errorMessage = "Error with instruction: " +
                ((label != null) ? label + " : " : "") + opcode + " "  +
                String.join(" ", params) + "\n";

        if (params.contains(null)) {
            System.err.println(errorMessage +
                    "Instruction parameters other than label cannot be null.");
            return null;
        }
        // Tests the Instruction exists
        if (Arrays.stream(BEAN_FACTORY.getBeanNamesForType(Instruction.class))
                .toList().contains(opcode)) {

            // Set up factory for Instruction construction
            PARAMETERS.add(label);
            PARAMETERS.addAll(params);
            correctFormatting = true;

            Instruction returnInstruction = (Instruction) BEAN_FACTORY.getBean(opcode);

            // Test whether anything went wrong trying to cast the parameter strings
            // for the instructions' constructor
            // and whether the correct number of parameters were input
            if (correctFormatting && PARAMETERS.isEmpty()) {
                return returnInstruction;
            }
            errorMessage = errorMessage + buildErrorMessage(returnInstruction.getClass().getConstructors(), params);
        } else {
            errorMessage = errorMessage + "No instruction of that name found.";
        }
        System.err.println(errorMessage);
        return null;
    }

    /**
     * Safely attempts to get an integer from PARAMETERS. See getObject.
     * @return an int. If the head of PARAMETERS fails to be cast to int, returns 0.
     */
    public static int getInt() {
        if (getObject(int.class) instanceof Integer integer)
            return integer;
        correctFormatting = false;
        return 0;
    }

    /**
     * Safely attempts to get a RegisterName from PARAMETERS. See getObject.
     * @return a RegisterName.
     * If the head of PARAMETERS fails to be cast to a RegisterName, returns NullRegisterName
     */
    public static RegisterName getRegisterName() {
        if (getObject(RegisterName.class) instanceof RegisterName regName)
            return regName;
        correctFormatting = false;
        return new NullRegisterName();
    }

    /**
     * Attempts to get a String from PARAMETERS.
     * Will always succeed so long is PARAMETERS is not empty. See getObject.
     * @return the head of PARAMETERS if PARAMETERS is not empty. Null otherwise
     */
    public static String getString() {
        if (getObject(String.class) instanceof String string)
            return string;
        return null;
    }

    /**
     * Overridden toString method.
     * Gives a list of the opcodes of all Instructions this factory can create.
     * @return a String containing a list of Instructions this factory can create
     */
    @Override
    public String toString() {
        return String.join(", ",
                Arrays.stream(BEAN_FACTORY.getBeanNamesForType(Instruction.class))
                .sorted().toList());
    }

}
