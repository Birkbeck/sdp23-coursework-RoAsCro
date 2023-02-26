package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.instruction.MovInstruction;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.Register.EAX;

public class InstructionFactoryTest {

    InstructionFactory fact;

    List<String> list;

    @BeforeEach
    void setUp() {
        fact = InstructionFactory.getInstance();
        list = new LinkedList<>();
    }

    @Test
    void testGetInstructionFactory() {
        Assertions.assertInstanceOf(InstructionFactory.class, fact);
    }

    @Test
    void testGetInstruction() {
        list.add("EAX");
        list.add("1");
        Instruction i = fact.getInstruction(null, "mov", list);

        Assertions.assertInstanceOf(MovInstruction.class, i);

        Machine m = new Machine(new Registers());
        i.execute(m);
        Assertions.assertEquals(1, m.getRegisters().get(EAX));
    }

    @Test
    void testGetInstructionInvalidInt() {
        list.add("EAX");
        list.add(null);
        Instruction i = fact.getInstruction(null, "mov", list);
        Assertions.assertNull(i);
    }

    @Test
    void testGetInstructionInvalidString() {
        list.add("EAX");
        list.add(null);
        Instruction i = fact.getInstruction(null, "jnz", list);
        Assertions.assertNull(i);

    }

    @Test
    void testGetInstructionInvalidRegName() {
        list.add(null);
        list.add("a");
        Instruction i = fact.getInstruction(null, "jnz", list);
        Assertions.assertNull(i);

    }

    @Test
    void testGetInstructionInvalidOpCode() {
        list.add("EAX");
        list.add("a");
        Instruction i = fact.getInstruction(null, null, list);
        Assertions.assertNull(i);

    }

    @Test
    void testExtensibility() {
        Machine m = new Machine(new Registers());

        Instruction i = fact.getInstruction("tes", "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);

        list.clear();
        list.add("EBX");
        list.add("3");
        i = fact.getInstruction(null, "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);

        list.clear();
        list.add("x");
        list.add("EBX");
        i = fact.getInstruction(null, "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);

        list.clear();
        list.add("3");
        i = fact.getInstruction(null, "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);

        list.clear();
        list.add("x");
        i = fact.getInstruction(null, "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);

        list.clear();
        list.add("EBX");
        i = fact.getInstruction(null, "tes", list);
        Assertions.assertNotNull(i);
        m.getProgram().add(i);
        
        //Test the factory prioritises non-string constructors
        assertEquals("tes 3 EAX a", m.getProgram().get(3).toString());
        assertEquals("tes 1 EAX x", m.getProgram().get(4).toString());
        assertEquals("tes 1 EBX a", m.getProgram().get(5).toString());
        m.execute();

    }


}
