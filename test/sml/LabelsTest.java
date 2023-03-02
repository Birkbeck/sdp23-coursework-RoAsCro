package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LabelsTest {

    private Labels labels;

    @BeforeEach
    public void setUp() {
        labels = new Labels();
    }

    @Test
    public void testAddDuplicateLabel() {
        Assertions.assertTrue(labels.addLabel("a", 0));
        Assertions.assertFalse(labels.addLabel("a", 1));
        Assertions.assertEquals(0, labels.getAddress("a"));
    }

    @Test
    public void testGetAddressNonexistentLabel() {
        Assertions.assertEquals(-1, labels.getAddress("a"));
    }

    @Test
    public void testToString() {
        labels.addLabel("a", 0);
        labels.addLabel("b", 3);
        Assertions.assertEquals("[a -> 0, b -> 3]", labels.toString());

    }

    @Test
    public void testEqualsAndHashCode() {
        // Test empty Labels
        Labels labels2 = new Labels();
        Assertions.assertEquals(labels, labels2);
        Assertions.assertEquals(labels.hashCode(), labels2.hashCode());

        // Should be different if the contents of labels are different
        labels.addLabel("a", 0);
        Assertions.assertNotEquals(labels, labels2);
        Assertions.assertNotEquals(labels.hashCode(), labels2.hashCode());

        // Should be the same if the contents are the same
        labels2.addLabel("a", 0);
        Assertions.assertEquals(labels, labels2);
        Assertions.assertEquals(labels.hashCode(), labels2.hashCode());

        // Test if the same if added in a different order
        labels.addLabel("c", 3);
        labels.addLabel("d", 5);
        labels2.addLabel("d", 5);
        labels2.addLabel("c", 3);
        Assertions.assertEquals(labels, labels2);
        Assertions.assertEquals(labels.hashCode(), labels2.hashCode());

        // Test if different if labels are the same, but location is different
        labels.addLabel("b", 1);
        labels2.addLabel("b", 2);
        Assertions.assertNotEquals(labels, labels2);
        Assertions.assertNotEquals(labels.hashCode(), labels2.hashCode());

    }

}
