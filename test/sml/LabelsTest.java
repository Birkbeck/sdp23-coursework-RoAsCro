package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LabelsTest {

    private Labels labels;

    @BeforeEach
    void setUp() {
        labels = new Labels();
    }

    @Test
    void testAddLabel() {
        labels.addLabel("a", 0);
        labels.addLabel("a", 1);
        Assertions.assertEquals(0, labels.getAddress("a"));
    }

    @Test
    void testGetAddress() {
        Assertions.assertEquals(-1, labels.getAddress("a"));
    }

    @Test
    void testToString() {
        labels.addLabel("a", 0);
        labels.addLabel("b", 3);
        Assertions.assertEquals("[a, b]", labels.toString());

    }

    @Test
    void testEquals() {
        Labels labels2 = new Labels();
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("a", 0);
        Assertions.assertNotEquals(labels, labels2);
        labels2.addLabel("a", 0);
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("b", 1);
        labels2.addLabel("b", 2);
        Assertions.assertNotEquals(labels, labels2);
    }

    @Test
    void testHashCode() {

    }

}
