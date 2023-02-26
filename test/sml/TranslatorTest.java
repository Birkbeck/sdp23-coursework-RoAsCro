package sml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedList;

public class TranslatorTest {

    private Translator translator;
    private LinkedList<Instruction> list;

    private final String fileLocation = "./testResources/";

    @BeforeEach
    void setUp(){
        list = new LinkedList<>();
    }

    @Test
    void testValidGetInstruction() {

        translator = new Translator(fileLocation + "test3.sml");
        assertDoesNotThrow(() -> translator.readAndTranslate(new Labels(), list));
        assertEquals("mov", list.get(0).getOpcode());
        assertEquals("out", list.get(3).getOpcode());
        assertEquals("sub", list.get(4).getOpcode());
        assertEquals("mul", list.get(6).getOpcode());
        assertEquals("div", list.get(7).getOpcode());

    }


    @Test
    void testDuplicateLabels() {

        translator = new Translator(fileLocation + "test5.sml");
        assertThrows(IOException.class,() -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void testInvalidFirstRegister(){
        translator = new Translator(fileLocation + "test6.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void testInvalidSecondRegister() {
        translator = new Translator(fileLocation + "test7.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void testNotANumber() {
        translator = new Translator(fileLocation + "test8.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void testUnknownInstruction() {
        translator = new Translator(fileLocation + "test10.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void tooFewArguments() {
        translator = new Translator(fileLocation + "test9.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }

    @Test
    void tooManyArguments() {
        translator = new Translator(fileLocation + "test13.sml");
        assertThrows(IOException.class, () -> translator.readAndTranslate(new Labels(), list));
    }


}
