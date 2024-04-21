package org.sviridov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class ConsoleUtilTest {

    @Test
    void testGetStringValue() {
        ByteArrayInputStream in = new ByteArrayInputStream("test.jpg".getBytes());
        System.setIn(in);
        assertEquals("test.jpg", ConsoleUtil.getStringValue(
                "testMsg",
                ".+(?=\\\\*)\\\\*[A-Za-z\\_0-9\\.-]+.(png|jpg|jpeg)")
        );
    }

    @Test
    void testGetStringValueWithInvalidValue() {
        ByteArrayInputStream in = new ByteArrayInputStream(
                "testFirstValue\ntestSecondValue.jpg".getBytes()
        );
        System.setIn(in);
        assertEquals("testSecondValue.jpg", ConsoleUtil.getStringValue(
                "testMsg",
                ".+(?=\\\\*)\\\\*[A-Za-z\\_0-9\\.-]+.(png|jpg|jpeg)")
        );
    }

    @Test
    void testGetIntegerValue() {
        ByteArrayInputStream in = new ByteArrayInputStream("30".getBytes());
        System.setIn(in);
        assertEquals(30, ConsoleUtil.getIntegerValue("testMsg"));
    }

    @Test
    void testIsIncorrectFileName() {
        assertFalse(ConsoleUtil.isIncorrectFileName(":&~|" + UUID.randomUUID()));
    }

    @Test
    void testGetIntegerValueWithSet() {
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
        HashSet<Integer> values = new HashSet<>();
        values.add(2);

        assertEquals(2, ConsoleUtil.getIntegerValue("testMsg", values));
    }
}