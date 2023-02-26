package sml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


public class InstructionFactory {

    private List<Instruction> instructions;

    private final Map<String, Class<? extends Instruction>> classMap = new HashMap<>();

    @Autowired
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    private void setClassMap() {

    }

    public Class<? extends Instruction> getInstructionClass(String opcode) {
        if (!classMap.containsKey(opcode))
            return null;
        return classMap.get(opcode);
    }

    public Instruction getInstruction(String label, String opcode, List<String> params) {
        Class<? extends Instruction> classus = getInstructionClass(opcode);
        if (classus == null) {
            //TODO print error message
            return null;
        }

        //Get constructors
        Constructor<?>[] constructors = classus.getConstructors();
        //Find only constructors that match the given number of parameters
        List<Constructor<?>> constructorList = Arrays.stream(constructors)
                .filter(c -> (c.getParameterCount() == params.size()+1))
                .toList();
        int noOfConstructors = constructorList.size();

        //If there are no constructors that fit the given number of parameters, display an error message
        if (noOfConstructors == 0) {
//            System.out.println(errorMessage);
//            System.out.println("Expected possible valid parameters: ");
//            for (Constructor<?> c : constructors) {
//                System.out.println(
//                        (c.getParameterCount() - 1) + " parameters: " +
//                                Arrays.stream(c.getParameterTypes())
//                                        .skip(1)
//                                        .map(Class::toString)
//                                        .collect(Collectors.joining(", ")));
//            }
//            System.out.println();
//            System.out.println("Got: " + params.size() + " parameters: " + params);
        }
        else {
            //Attempt to match the given parameters with the types of the parameters of the remaining constructors
            List<Object> list = new LinkedList<>();

            for (Constructor<?> c : constructorList) {
                Class<?>[] types = c.getParameterTypes();
                list.add(label);
                //The first parameter is skipped as that's the label
                for (int i = 1; i < types.length; i++) {
                    Class<?> clss = types[i];
                    String parameter = params.get(i - 1);

                    if (clss == RegisterName.class) {
                        try {
                            list.add(Registers.Register.valueOf(parameter));
                        } catch (IllegalArgumentException e) {
                            list.clear();
                            break;
                        }
                    } else if (clss == int.class) {
                        try {
                            list.add(Integer.parseInt(parameter));
                        } catch (NumberFormatException e) {
                            list.clear();
                            break;
                        }
                    } else if (clss == String.class) {
                        list.add(parameter);
                    }
                }
                if (list.size() != 0)
                    try {
                        return (Instruction) c.newInstance(list.toArray());
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                else {
                    //TODO: display error message
                }

            }
        }
        return null;
    }

    private InstructionFactory() {}

    public static InstructionFactory getInstructionFactory() {
        InstructionFactory factory = (InstructionFactory) new ClassPathXmlApplicationContext("instructions.xml").getBean("insFactory");
        for (Instruction i : factory.instructions) {
            factory.classMap.put(i.opcode, i.getClass());
        }
        return factory;
    }



}
