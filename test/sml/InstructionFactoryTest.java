package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.instruction.AddInstruction;
import sml.instruction.MovInstruction;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.Register.EAX;

public class InstructionFactoryTest {

    private InstructionFactory fact;

    private List<String> list;

    @BeforeEach
    public void setUp() {
        fact = InstructionFactory.getInstance();
        list = new LinkedList<>();
    }

    @Test
    public void testGetInstructionFactory() {
        Assertions.assertInstanceOf(InstructionFactory.class, fact);
    }

    @Test
    public void testGetInstruction() {
        list.add("EAX");
        list.add("1");
        Instruction i = fact.getInstruction(null, "mov", list);

        Assertions.assertInstanceOf(MovInstruction.class, i);

        Machine m = new Machine(new Registers());
        i.execute(m);
        Assertions.assertEquals(1, m.getRegisters().get(EAX));
    }

    @Test
    public void testGetInstructionAdd() {
        list.add("EAX");
        list.add("EBX");
        Instruction i = fact.getInstruction(null, "add", list);

        Assertions.assertInstanceOf(AddInstruction.class, i);
        Assertions.assertEquals("add EAX EBX", i.toString());
        Machine m = new Machine(new Registers());
        i.execute(m);

    }

    @Test
    public void testGetInstructionInvalidInt() {
        list.add("EAX");
        list.add(null);
        Instruction i = fact.getInstruction(null, "mov", list);
        Assertions.assertNull(i);
    }

    @Test
    public void testGetInstructionInvalidString() {
        list.add("EAX");
        list.add(null);
        Instruction i = fact.getInstruction(null, "jnz", list);
        Assertions.assertNull(i);

    }

    @Test
    public void testGetInstructionInvalidRegName() {
        list.add(null);
        list.add("a");
        Instruction i = fact.getInstruction(null, "jnz", list);
        Assertions.assertNull(i);

    }

    @Test
    public void testGetInstructionInvalidOpCode() {
        list.add("EAX");
        list.add("a");
        Instruction i = fact.getInstruction(null, null, list);
        Assertions.assertNull(i);

    }
    
}
