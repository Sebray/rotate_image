import java.awt.*;
import java.awt.image.BufferedImage;

public class Smoothing {
    private Smoothing() {
        throw new IllegalStateException("Utility class for rotation");
    }

    public static void smoothOut(BufferedImage image) {
        for (int i = 1; i < image.getWidth() - 1; i++)
            for (int j = 1; j < image.getHeight() - 1; j++) {
                if (image.getRGB(i, j) == 0 && existAllNeighboring(i, j, image))
                    image.setRGB(i, j, calcAverageColor(image, i, j).getRGB());
            }
    }

    private static boolean existAllNeighboring(int x, int y, BufferedImage image) {
        for (int i = -1; i <= 1; i += 2)
            for (int j = -1; j <= 1; j += 2)
                if (image.getRGB(x + i, y + j) == 0)
                    return false;
        return true;
    }

    private static float doLerp(float s, float e, float t) {
        return s + (e - s) * t;
    }

    private static float doBlerp(float c00, float c10, float c01, float c11, float tx, float ty) {
        return doLerp(doLerp(c00, c10, tx), doLerp(c01, c11, tx), ty);
    }

    private static Color calcAverageColor(BufferedImage image, int x, int y) {
        float gx = ((float) x) / image.getWidth();
        float gy = ((float) y) / image.getHeight();

        Color c00 = new Color(image.getRGB(x - 1, y));
        Color c10 = new Color(image.getRGB(x + 1, y));
        Color c01 = new Color(image.getRGB(x, y + 1));
        Color c11 = new Color(image.getRGB(x, y - 1));
        x = (int) gx;
        y = (int) gy;

        int red = (int) doBlerp(c00.getRed(), c10.getRed(), c01.getRed(), c11.getRed(), gx - x, gy - y);
        int green = (int) doBlerp(c00.getGreen(), c10.getGreen(), c01.getGreen(), c11.getGreen(), gx - x, gy - y);
        int blue = (int) doBlerp(c00.getBlue(), c10.getBlue(), c01.getBlue(), c11.getBlue(), gx - x, gy - y);
        return new Color(red, green, blue);
    }
}