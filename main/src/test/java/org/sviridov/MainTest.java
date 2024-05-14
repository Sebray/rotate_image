package org.sviridov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainTest {
    @Test
    void testInitChoices() {
        Set<Integer> expectedValues = new HashSet<>();
        expectedValues.add(Main.FIRST);
        expectedValues.add(Main.SECOND);

        assertArrayEquals(expectedValues.toArray(), Main.initChoices().toArray());
    }

    @Test
    void testDoChoice() {
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
        assertEquals(2, Main.doChoice());
    }

    @Test
    void testDoRotationBySimpleMethod() {
        BufferedImage image = new BufferedImage(100, 257, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> Main.doRotation(image, 150));
    }

    @Test
    void testDoRotationBy3ShareMethod() {
        BufferedImage image = new BufferedImage(100, 257, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> Main.doRotation(image, 149));
    }

    @Test
    void testDoSmoothingWithNearPixel() {
        BufferedImage image = new BufferedImage(100, 257, BufferedImage.TYPE_INT_ARGB);
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
        assertDoesNotThrow(() -> Main.doSmoothing(image));
    }

    @Test
    void testDoSmoothing() {
        BufferedImage image = new BufferedImage(140, 257, BufferedImage.TYPE_INT_ARGB);
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        assertDoesNotThrow(() -> Main.doSmoothing(image));
    }

    @Test
    void testWriteResultImage() {
        BufferedImage image = new BufferedImage(100, 257, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> Main.writeResultImage(UUID.randomUUID().toString(), image));
    }
}