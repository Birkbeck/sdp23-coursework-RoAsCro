package sml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public Instruction getInstruction(List<String> params) {
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
