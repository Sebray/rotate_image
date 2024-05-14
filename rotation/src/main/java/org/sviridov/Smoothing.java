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
                        && existAllNeighboring(i, j, image)) {
                    image.setRGB(i, j, calcColor(image, i, j));
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
            final int coordinate,
            final int secondCoordinate,
            final BufferedImage image
    ) {
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                if (image.getRGB(coordinate + i, secondCoordinate + j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static float doLinearInterpolation(
            final float start,
            final float end,
            final float value
    ) {
        return start + (end - start) * value;
    }

    private static float doBilinearInterpolation(
            final float c00,
            final float c10,
            final float c01,
            final float c11,
            final float tx,
            final float ty
    ) {
        return doLinearInterpolation(
                doLinearInterpolation(c00, c10, tx),
                doLinearInterpolation(c01, c11, tx),
                ty
        );
    }

    private static int calcColor(
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

        int red = (int) doBilinearInterpolation(
                c00.getRed(),
                c10.getRed(),
                c01.getRed(),
                c11.getRed(),
                gx - intX,
                gy - intY
        );
        int green = (int) doBilinearInterpolation(
                c00.getGreen(),
                c10.getGreen(),
                c01.getGreen(),
                c11.getGreen(),
                gx - intX,
                gy - intY
        );
        int blue = (int) doBilinearInterpolation(
                c00.getBlue(),
                c10.getBlue(),
                c01.getBlue(),
                c11.getBlue(),
                gx - x,
                gy - intY
        );
        return new Color(red, green, blue).getRGB();
    }
}
