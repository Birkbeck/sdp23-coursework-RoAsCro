package sml;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.instruction.AddInstruction;

import java.util.LinkedList;
import java.util.List;

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
    void testMap() {
        Assertions.assertEquals(AddInstruction.class, fact.getInstructionClass("add"));
    }

    @Test
    void testInstruction() {
        List<String> list = new LinkedList<String>();
        list.add(null);
        list.add("add");
        list.add("EAX");
        list.add("EBX");
        Assertions.assertInstanceOf(AddInstruction.class, fact.getInstruction(list));
    }


}
