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
        Assertions.assertTrue(labels.addLabel("a", 0));
        Assertions.assertFalse(labels.addLabel("a", 1));
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
        Assertions.assertEquals("[a -> 0, b -> 3]", labels.toString());

    }

    @Test
    void testEquals() {
        Labels labels2 = new Labels();
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("a", 0);
        Assertions.assertNotEquals(labels, labels2);
        labels2.addLabel("a", 0);
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("c", 3);
        labels.addLabel("d", 5);
        labels2.addLabel("d", 5);
        labels2.addLabel("c", 3);
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("b", 1);
        labels2.addLabel("b", 2);
        Assertions.assertNotEquals(labels, labels2);
    }

    @Test
    void testHashCode() {
        Labels labels2 = new Labels();
        Assertions.assertEquals(labels.hashCode(), labels2.hashCode());
        labels.addLabel("a", 0);
        Assertions.assertNotEquals(labels.hashCode(), labels2.hashCode());
        labels2.addLabel("a", 0);
        Assertions.assertEquals(labels.hashCode(), labels2.hashCode());
        labels.addLabel("c", 3);
        labels.addLabel("d", 5);
        labels2.addLabel("d", 5);
        labels2.addLabel("c", 3);
        Assertions.assertEquals(labels, labels2);
        labels.addLabel("b", 1);
        labels2.addLabel("b", 2);
        Assertions.assertNotEquals(labels.hashCode(), labels2.hashCode());
    }

}
