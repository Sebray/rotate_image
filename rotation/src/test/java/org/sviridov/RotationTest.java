package org.sviridov;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class RotationTest {

    @Test
    void testRotateBySimpleMethod() {
        BufferedImage image = new BufferedImage(4,4, BufferedImage.TYPE_INT_ARGB);
        int k = 20;
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                image.setRGB(i, j, k);
            k+=20;
            }
        }
        Rotation rotation = new Rotation(image, 90);
        BufferedImage newImage = rotation.rotateBySimpleMethod();
        assertEquals(image.getRGB(1,2), newImage.getRGB(2,3));

    }
}