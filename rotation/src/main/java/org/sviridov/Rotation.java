package org.sviridov;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Rotation {
    /** Радиан. */
    private double radian;
    /** Изображение для обработки. */
    private BufferedImage image;
    /** Новая ширина. */
    private int newWidth;
    /** Новая высота. */
    private int newHeight;
    /** Исходная центральная точка. */
    private Point center;
    /** Новая центральная точка. */
    private Point newCenter;

    /**
     * Сглаживание изображения по сосденим пикселям.
     * @param currentImage изображение, которое нужно обработать
     * @param angle угол поворота
     */
    public Rotation(final BufferedImage currentImage, final int angle) {
        final int degree = 180;
        radian = angle * Math.PI / degree;

        int height = (int) (Math.round(
                Math.abs(currentImage.getHeight() * Math.cos(radian))
                        + Math.abs(currentImage.getWidth() * Math.sin(radian))
        ) + 1);
        int width = (int) (Math.round(
                Math.abs(currentImage.getWidth() * Math.cos(radian))
                        + Math.abs(currentImage.getHeight() * Math.sin(radian))
        ) + 1);

        final int indent = 5;
        this.image = currentImage;
        this.newWidth = width + indent;
        this.newHeight = height + indent;
        center = new Point(
                (int) Math.round((currentImage.getWidth() + 1) / 2.0 - 1),
                (int) Math.round((currentImage.getHeight() + 1) / 2.0 - 1)
        );
        newCenter = new Point(
                (int) Math.round((width + 1) / 2.0 - 1) + 1,
                (int) Math.round((height + 1) / 2.0 - 1) + 1
        );
    }

    /**
     * Поворот иозображения простым способом.
     * @return Перевернутое изображение
     * */
    public BufferedImage rotateBySimpleMethod() {
        int newX;
        int newY;
        int x;
        int y;
        BufferedImage resultImage = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                y = image.getHeight() - 1 - h - center.y;
                x = image.getWidth() - 1 - w - center.x;

                newX = (int) (Math.round(
                        x * Math.cos(radian) - y * Math.sin(radian)
                ));
                newY = (int) (Math.round(
                        x * Math.sin(radian) + y * Math.cos(radian)
                ));

                newY = newCenter.y - newY;
                newX = newCenter.x - newX;

                resultImage.setRGB(newX, newY, image.getRGB(w, h));
            }
        }
        return resultImage;
    }

    /**
     * Поворот иозображения при помощи сдвигов.
     * @return Перевернутое изображение
     * */
    public BufferedImage rotateBy3ShareMethod() {
        int newX;
        int newY;
        int x;
        int y;
        double tangent;

        BufferedImage res = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                y = image.getHeight() - 1 - h - center.y;
                x = image.getWidth() - 1 - w - center.x;

                // 1 сдиг
                tangent = Math.tan(radian / 2);
                newX = (int) Math.round(x - y * tangent);
                newY = y;

                // 2 сдиг
                newY = (int) Math.round(newX * Math.sin(radian) + newY);

                //3 сдиг
                newX = (int) Math.round(newX - newY * tangent);

                newY = newCenter.y - newY;
                newX = newCenter.x - newX;

                res.setRGB(newX, newY, image.getRGB(w, h));
            }
        }
        return res;
    }
}
