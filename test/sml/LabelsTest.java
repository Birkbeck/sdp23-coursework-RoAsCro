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

    }

    @Test
    void testToString() {

    }

    @Test
    void testEquals() {

    }

    @Test
    void testHashCode() {

    }

}
