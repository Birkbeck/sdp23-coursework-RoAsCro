package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sml.Registers.Register;

/**
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program. Takes a text file on construction containing an SML program
 * to be translated, converting it into Instructions executable by a Machine.
 *
 * @author ...
 * @author Roland Crompton
 */
public final class Translator {

    /**
     * The source file of the SML code
     */
    private final String fileName;

    /**
     * line contains the characters in the current line that's not been processed yet
     */
    private String line = "";

    /**
     * Constructs a Translator taking the String fileName as the location of the source file of the SML program.
     * @param fileName the source file of the SML program.
     */
    public Translator(String fileName) {
        this.fileName =  fileName;
    }


//    translate the small program in the file into lab (the labels) and
//     prog (the program)
//     return "no errors were detected"

    /**
     * Translates the SML program in the file into a series of Instructions executable by a Machine. Instructions are
     * stored in program parameter, and Labels in the labels parameter.
     *
     * @param labels an instance of Labels where the labels of the instructions in the program will be stored
     * @param program a list of Instructions where the translated Instructions will be stored
     * @throws IOException if something goes wrong in the process of reading the file, including finding errors in the
     * formatting of the SML file
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        if (!labels.addLabel(label, program.size()))
                            throw new IOException();
                    program.add(instruction);
                } else
                    throw new IOException();
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction. Null if the instruction isn't valid for any  reason.
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();

        List<String> params;
        int[] l = {-1};
        params = Stream.generate(this::scan).takeWhile(x -> {
            boolean y = line.length() != l[0];
            l[0] = line.length();
            return y;})
                .toList();

        int correctParamNumber = 0;
        String errorMessage = "Error with instruction: " +
                opcode + " "  +
                String.join(" ", params)
                +
                "\n";
        String classOpCode = opcode.substring(0, 1).toUpperCase() + opcode.replace(opcode.substring(0,1), "");
        Constructor<?>[] constructors = new Constructor<?>[0];
        try {
            //TODO: Account for instructions named differently
            //TODO: See about converting that for loop into a stream
            //TODO: Come up with better exception handling
            Class<?> instructionClass = Class.forName("sml.instruction."+classOpCode+"Instruction");
            constructors = instructionClass.getConstructors();
            List<Constructor<?>> constructorList = Arrays.stream(constructors).filter(c -> (c.getParameterCount() == params.size()+1)).toList();
            int noOfConstructors = constructorList.size();

            if (noOfConstructors == 0) {
                System.out.println(errorMessage);
                System.out.println("Expected possible valid parameters: ");
                for (Constructor<?> c : constructors) {
                    System.out.println(
                            (c.getParameterCount() - 1) + " parameters: " +
                                    Arrays.stream(c.getParameterTypes())
                                            .skip(1)
                                            .map(Class::toString)
                                            .collect(Collectors.joining(", ")));
                }
                System.out.println();
                System.out.println("Got: " + params.size() + " parameters: " + params);
            }
            else {
                List<Object> list = new LinkedList<>();


                for (Constructor<?> c : constructorList) {
                    Class<?>[] types = c.getParameterTypes();
                    list.clear();
                    list.add(label);
                    for (int i = 1; i < params.size() + 1; i++) {
                        Class<?> clss = types[i];
                        if (clss == RegisterName.class) {
                            try {
                                list.add(Register.valueOf(params.get(i - 1)));
                            } catch (IllegalArgumentException e) {
                                list.clear();
                                break;
                            }
                        } else if (clss == int.class) {
                            try {
                                list.add(Integer.parseInt(params.get(i - 1)));
                            } catch (NumberFormatException e) {
                                list.clear();
                                break;
                            }
                        } else if (clss == String.class) {
                            list.add(params.get(i - 1));
                        }
                    }

                }
                if (list.size() != 0)
                    return (Instruction) constructors[0].newInstance(list.toArray());
            }

//                // TODO: add code for all other types of instructions
//
//                // TODO: Then, replace the switch by using the Reflection API
//
//                // TODO: Next, use dependency injection to allow this machine class
//                //       to work with different sets of opcodes (different CPUs)

//        } catch (NumberFormatException e) {
//            System.out.println(errorMessage + opcode + " instruction requires an integer.");
//        } catch (IllegalArgumentException e) {
//            System.out.println(errorMessage + "One or more registers not found in machine.");
//        } catch (IndexOutOfBoundsException e) {
            
//            System.out.println(errorMessage + "Expected " + correctParamNumber + " parameters. Got " + params.size());
        } catch (ClassNotFoundException e) {
            System.out.println("Unknown instruction: " + opcode);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Checks if the current line of the file contains a label.
     * @return the label if there is one, null if there is not.
     */
    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }


    /**
     * Return the first word of the current line and remove it from the line.
     *
     * @return the first word of the line if there is one, otherwise the line.
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}