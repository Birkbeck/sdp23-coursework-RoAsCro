package sml;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.instruction.AddInstruction;
import sml.instruction.MovInstruction;

import java.util.LinkedList;
import java.util.List;

import static sml.Registers.Register.EAX;

public class InstructionFactoryTest {

    InstructionFactory fact;

    @BeforeEach
    void setUp() {
        fact = InstructionFactory.getInstructionFactory();
    }

    @Test
    void testGetInstructionFactory() {
        Assertions.assertInstanceOf(InstructionFactory.class, fact);
    }

    @Test
    void testInstruction() {
        List<String> list = new LinkedList<String>();
        list.add("EAX");
        list.add("1");
        Instruction i = fact.getInstruction(null, "mov", list);

        Assertions.assertInstanceOf(MovInstruction.class, i);

        Machine m = new Machine(new Registers());
        i.execute(m);
        Assertions.assertEquals(1, m.getRegisters().get(EAX));
    }


}
