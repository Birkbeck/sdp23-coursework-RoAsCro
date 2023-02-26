package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InstructionFactory {

    private List<Instruction> instructions;

    private final Map<String, Class<? extends Instruction>> classMap = new HashMap<>();

    public static class  NullRegisterName implements RegisterName {
        @Override
        public String name() {
            return " ";
        }
    }

    private final Map<Class<?>, Function<String, ?>> paramTypes = Map.of(
            String.class, x -> x,
            RegisterName.class, Registers.Register::valueOf,
            int.class, Integer::parseInt
            );

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }


    private Class<? extends Instruction> getInstructionClass(String opcode) {
        if (!classMap.containsKey(opcode))
            return null;
        return classMap.get(opcode);
    }

    public Instruction getInstruction(String label, String opcode, List<String> params) {
        //TODO Handle null inputs
        Class<? extends Instruction> classus = getInstructionClass(opcode);

        String errorMessage = "Error with instruction: " +
                ((label != null) ? label + " : " : "") +
                opcode + " "  +
                String.join(" ", params)
                +
                "\n";

        if (classus == null) {
            System.err.println(errorMessage + "No such instruction found.");
            return null;
        }

        //Get constructors
        Constructor<?>[] constructors = classus.getConstructors();

        StringBuilder paramsErrorMessage = new StringBuilder(errorMessage +
                "Possible sets of valid parameters for instruction type " + opcode + ":");
        Arrays.stream(constructors).forEach( constructor ->
                paramsErrorMessage.append("\n").append(constructor.getParameterCount() - 1).append(" parameters: ").append(Arrays.stream(constructor.getParameterTypes())
                        .skip(1)
                        .map(Class::getName)
                        .collect(Collectors.joining(", "))));
        paramsErrorMessage.append("\nGot: ").append(params.size()).append(" parameters: ").append(params);

        //Find only constructors that match the given number of parameters
        List<Constructor<?>> constructorList = Arrays.stream(constructors)
                .filter(c -> (c.getParameterCount() == params.size()+1))
                .toList();
        int noOfConstructors = constructorList.size();

        //If there are no constructors that fit the given number of parameters, display an error message
        if (noOfConstructors == 0) {
            System.err.println(paramsErrorMessage);
            System.err.println("Invalid number of parameters.");
        }
        else {
            //Attempt to match the given parameters with the types of the parameters of the remaining constructors
            List<Object> typedParams = new LinkedList<>();

            for (Constructor<?> c : constructorList) {
                Class<?>[] types = c.getParameterTypes();
                typedParams.add(label);

                //The first parameter is skipped as that's the label
                Iterator<Class<?>> typeIter = Arrays.stream(types).skip(1).iterator();
                for (String p : params) {
                    try {
                        typedParams.add(paramTypes.get(typeIter.next()).apply(p));
                    } catch (IllegalArgumentException e) {
                        typedParams.clear();
                        break;
                    }
                }
                if (typedParams.size() != 0)
                    try {
                        return (Instruction) c.newInstance(typedParams.toArray());
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        typedParams.clear();
                    }

            }
        }
        System.err.println(paramsErrorMessage);
        System.err.println("Invalid parameter types.");
        return null;
    }

    private InstructionFactory() {}

    public static InstructionFactory getInstructionFactory() {
        InstructionFactory factory = (InstructionFactory) new ClassPathXmlApplicationContext("instructions.xml").getBean("insFactory");
        factory.instructions.forEach(i -> factory.classMap.put(i.opcode, i.getClass()));
        return factory;
    }



}
