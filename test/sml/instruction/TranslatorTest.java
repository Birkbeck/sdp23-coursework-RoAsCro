package sml.instruction;

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

    @BeforeEach
    void setUp(){
        translator = new Translator("./cw/src/test3.sml");
    }

    @Test
    void testGetInstruction() throws IOException {
        LinkedList<Instruction> list = new LinkedList<>();
        translator.readAndTranslate(new Labels(), list);
        assertEquals("mov", list.get(0).getOpcode());
    }

}
