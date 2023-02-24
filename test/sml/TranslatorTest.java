package sml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import sml.Instruction;
import sml.Labels;
import sml.Translator;

import java.io.IOException;
import java.util.LinkedList;

public class TranslatorTest {

    private Translator translator;
    private LinkedList<Instruction> list;

    @BeforeEach
    void setUp(){
         list = new LinkedList<>();
    }

    @Test
    void testValidGetInstruction() {

        translator = new Translator("./cw/src/test3.sml");
        try {
            translator.readAndTranslate(new Labels(), list);
            assertEquals("mov", list.get(0).getOpcode());
            assertEquals("out", list.get(3).getOpcode());
            assertEquals("sub", list.get(4).getOpcode());
            assertEquals("mul", list.get(6).getOpcode());
            assertEquals("div", list.get(7).getOpcode());
        } catch (IOException e) {
            System.out.println("Test failed.");
        }
    }


    @Test
    void testDuplicateLabels() {
        translator = new Translator("./cw/src/test5.sml");
        assertThrows(IOException.class,() -> translator.readAndTranslate(new Labels(), list));
    }

}
