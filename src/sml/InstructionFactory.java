package sml;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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

    static boolean correctFormatting;

    private static LinkedList<String> objects = new LinkedList<>();

    private static ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("instructions.xml");


    /**
     * The list containing instances possible instructions constructed with default null values. These should not be
     * used for anything except registering Instructions in the classMap. Automatically loaded via Spring autowiring
     * when getInstance is called for the first time when running a program via autowiring.
     */
//    private List<Instruction> instructions;

    /**
     * A map storing Instruction opcodes and the classes to which they correspond. Used for instantiating Instructions
     * from their opcodes.
     */
//    private final Map<String, Class<? extends Instruction>> classMap = new HashMap<>();

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
//    public void setInstructions(List<Instruction> instructions) {
//        this.instructions = instructions;
//    }

    /**
     * Safely gets the Class of the instruction corresponding to the given opcode.
//     * @param opcode the opcode of the Instruction Class to be returned
     * @return the class of the Instruction corresponding to the opcode. Null if the opcode does not belong to a valid
     * instruction
     */
//    private Class<? extends Instruction> getInstructionClass(String opcode) {
//        if (!classMap.containsKey(opcode))
//            return null;
//        return classMap.get(opcode);
//    }

    private static Object getObject(Class<?> targetClass) {
        if (!objects.isEmpty()) {
            System.out.println("Yes");
            System.out.println();
            Object returnObject = castString(objects.pop(), targetClass);
            return returnObject;
        }
        System.out.println("OOPS");
        return null;
    }

    public static String getString() {
        System.out.println("String");
        Object o = getObject(String.class);
        if (o instanceof String s)
            return s;
        correctFormatting = false;
        return "";
    }

    public static RegisterName getRegisterName() {
        System.out.println("RegName");
        Object o = getObject(RegisterName.class);
        if (o instanceof RegisterName r)
            return r;
        correctFormatting = false;
        return new NullRegisterName();
    }

    public static int getInt() {
        System.out.println("Int");
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
        String errorMessage = "Error with instruction: " + ((label != null) ? label + " : " : "") + opcode + " "  +
                String.join(" ", params) + "\n";
        if (opcode == null || params.contains(null)) {
            return null;
        }
        try {
            objects.add(Objects.requireNonNullElse(label, ""));
            objects.addAll(params);
            correctFormatting = true;
            Instruction returnInstruction = (Instruction) beanFactory.getBean(opcode);
            System.out.println(returnInstruction);

            if (correctFormatting && objects.isEmpty()) {
                    System.out.println(returnInstruction);
                    return returnInstruction;
                }
            return null;
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println(errorMessage + "No instruction of that name found.");
            return null;
        }
    }

    /**
     * Attempts to convert the given String into the given type. Type should only be String, RegisterName, or int.
     * @param input the string to be converted
     * @param type the type the input is to be converted to. Must be String, RegisterName, or int
     * @return the converted input if it's possible to convert it, null otherwise
     */
    private static Object castString(String input, Class<?> type) {
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
        objects.clear();
        InstructionFactory factory =
                (InstructionFactory) beanFactory
                        .getBean("insFactory");
//        factory.instructions.forEach(i -> factory.classMap.put(i.getOpcode(), i.getClass()));
        System.out.println(factory);

        return factory;
    }

    /**
     * Overridden toString method. Gives a list of the opcodes of all Instructions loaded into the factory at
     * construction in alphabetical order.
     * @return a String containing a list of Instructions this factory can create
     */
    @Override
    public String toString() {
//        return String.join(", ", classMap.keySet().stream().sorted().toList());
        return "";
    }



}
