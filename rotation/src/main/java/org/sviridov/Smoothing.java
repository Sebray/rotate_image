package org.sviridov;

import java.awt.Color;
import java.awt.image.BufferedImage;

public final class Smoothing {
    private Smoothing() {
        throw new IllegalStateException("Utility class for rotation");
    }

    /**
     * Сглаживание изображения.
     * @param image изображение, которое нужно обработать
     */
    public static void smoothOut(final BufferedImage image) {
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                if (image.getRGB(i, j) == 0
                        && existAllNeighboring(i, j, image)
                ) {
                    image.setRGB(i, j, calcAverageColor(image, i, j).getRGB());
                }
            }
        }
    }

    /**
     * Сглаживание изображения по сосденему пикселю.
     * @param image изображение, которое нужно обработать
     */
    public static void smoothOutByNearPixel(final BufferedImage image) {
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                if (image.getRGB(i, j) == 0) {
                    image.setRGB(i, j, image.getRGB(i + 1, j));
                }
            }
        }
    }

    private static boolean existAllNeighboring(
            final int x,
            final int y,
            final BufferedImage image
    ) {
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                if (image.getRGB(x + i, y + j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static float doLerp(
            final float s,
            final float e,
            final float t
    ) {
        return s + (e - s) * t;
    }

    private static float doBlerp(
            final float c00,
            final float c10,
            final float c01,
            final float c11,
            final float tx,
            final float ty
    ) {
        return doLerp(
                doLerp(c00, c10, tx),
                doLerp(c01, c11, tx),
                ty
        );
    }

    private static Color calcAverageColor(
            final BufferedImage image,
            final int x,
            final int y
    ) {
        float gx = ((float) x) / image.getWidth();
        float gy = ((float) y) / image.getHeight();

        Color c00 = new Color(image.getRGB(x - 1, y));
        Color c10 = new Color(image.getRGB(x + 1, y));
        Color c01 = new Color(image.getRGB(x, y + 1));
        Color c11 = new Color(image.getRGB(x, y - 1));
        int intX = (int) gx;
        int intY = (int) gy;

        int red = (int) doBlerp(
                c00.getRed(),
                c10.getRed(),
                c01.getRed(),
                c11.getRed(),
                gx - intX,
                gy - intY
        );
        int green = (int) doBlerp(
                c00.getGreen(),
                c10.getGreen(),
                c01.getGreen(),
                c11.getGreen(),
                gx - intX,
                gy - intY
        );
        int blue = (int) doBlerp(
                c00.getBlue(),
                c10.getBlue(),
                c01.getBlue(),
                c11.getBlue(),
                gx - intX,
                gy - intY
        );
        return new Color(red, green, blue);
    }
}
