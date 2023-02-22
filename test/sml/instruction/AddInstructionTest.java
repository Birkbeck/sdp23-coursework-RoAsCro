package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class AddInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    //...
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  void executeValid() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(11, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(new AddInstruction("x", EAX, EBX), new AddInstruction("x", EAX, EBX));
    Assertions.assertEquals(new AddInstruction(null, EAX, EBX), new AddInstruction(null, EAX, EBX));
    Assertions.assertNotEquals(new AddInstruction(null, EAX, EBX), new AddInstruction("x", EAX, EBX));
    Assertions.assertNotEquals(new AddInstruction(null, ECX, EBX), new AddInstruction(null, EAX, EBX));
  }

  @Test
  void testHashCode() {
    Assertions.assertEquals(new AddInstruction("x", EAX, EBX).hashCode(), new AddInstruction("x", EAX, EBX).hashCode());
    Assertions.assertEquals(new AddInstruction(null, EAX, EBX).hashCode(), new AddInstruction(null, EAX, EBX).hashCode());
    Assertions.assertNotEquals(new AddInstruction(null, EAX, EBX).hashCode(), new AddInstruction("x", EAX, EBX).hashCode());
    Assertions.assertNotEquals(new AddInstruction(null, ECX, EBX).hashCode(), new AddInstruction(null, EAX, EBX).hashCode());
    Assertions.assertNotEquals(new AddInstruction(null, ECX, EBX).hashCode(), new SubInstruction(null, ECX, EBX).hashCode());
  }

}