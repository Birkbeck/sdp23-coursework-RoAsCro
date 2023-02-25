package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sml.instruction.AddInstruction;
import static sml.Registers.Register.*;

public class MachineTest {
    @Test
    void testEqualsAndHash() {
        Machine m = new Machine(new Registers());
        Machine n = new Machine(new Registers());

        //Test equal when initialised
        Assertions.assertEquals(m, m);
        Assertions.assertEquals(m, n);
        Assertions.assertEquals(m.hashCode(), n.hashCode());

        //Test not equal when program not the same
        m.getProgram().add(new AddInstruction(null, EAX, EBX));
        Assertions.assertNotEquals(m, n);
        Assertions.assertNotEquals(m.hashCode(), n.hashCode());

        //Test equal when program the same, even though it's two different instances of the same kind of instruction
        n.getProgram().add(new AddInstruction(null, EAX, EBX));
        Assertions.assertEquals(m, n);
        Assertions.assertEquals(m.hashCode(), n.hashCode());

        //Test not equal once one program is executed: program counter in a different place (registers remain the same in
        // this case)
        m.execute();
        Assertions.assertNotEquals(m, n);
        Assertions.assertNotEquals(m.hashCode(), n.hashCode());

        //Test not equal when identical in all but labels
        Machine o = new Machine(new Registers());
        o.getProgram().add(new AddInstruction("x", EAX, EBX));
        Assertions.assertNotEquals(o, n);
        Assertions.assertNotEquals(o.hashCode(), n.hashCode());

        //Test when identical in all but registers
        n.execute();
        n.getRegisters().set(EAX, 1);
        Assertions.assertNotEquals(m, n);
        Assertions.assertNotEquals(m.hashCode(), n.hashCode());
    }
}
