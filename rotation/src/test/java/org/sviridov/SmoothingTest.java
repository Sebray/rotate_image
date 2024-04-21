package org.sviridov;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
class SmoothingTest {

    @Test
    void testSmoothOut() {
        BufferedImage image = new BufferedImage(4,4, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4; j++){
                image.setRGB(i, j, Color.CYAN.getRGB());
            }
        }

        //Пиксель 2:2 пустой
        image.setRGB(2, 2, 0);

        Smoothing.smoothOut(image);
        assertEquals(Color.CYAN.getRGB(), image.getRGB(2,2));

    }

    @Test
    void testSmoothOutByNearPixel() {
        BufferedImage image = new BufferedImage(4,4, BufferedImage.TYPE_INT_ARGB);
        //Пиксель 1:1 пустой
        image.setRGB(2, 1, Color.ORANGE.getRGB());

        Smoothing.smoothOutByNearPixel(image);
        assertEquals(Color.ORANGE.getRGB(), image.getRGB(1,1));
    }
}