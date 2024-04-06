package org.sviridov;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotation {
    private double radian;
    private BufferedImage image;
    private int newWidth, newHeight;
    private Point center, newCenter;

    public Rotation(BufferedImage image, int angle) {
        radian = angle * Math.PI / 180;

        int newHeight = (int) (Math.round(Math.abs(image.getHeight() * Math.cos(radian))
                + Math.abs(image.getWidth() * Math.sin(radian))) + 1);
        int newWidth = (int) (Math.round(Math.abs(image.getWidth() * Math.cos(radian))
                + Math.abs(image.getHeight() * Math.sin(radian))) + 1);

        this.image = image;
        this.newWidth = newWidth + 10;
        this.newHeight = newHeight + 10;
        center = new Point((int) Math.round((image.getWidth() + 1) / 2.0 - 1),
                (int) Math.round((image.getHeight() + 1) / 2.0 - 1));
        newCenter = new Point((int) Math.round((newWidth + 1) / 2.0 - 1) + 1,
                (int) Math.round((newHeight + 1) / 2.0 - 1) + 1);
    }

    public BufferedImage rotateBySimpleMethod() {
        int newX, newY, x, y;
        BufferedImage res = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int h = 0; h < image.getHeight(); h++)
            for (int w = 0; w < image.getWidth(); w++) {
                y = image.getHeight() - 1 - h - center.y;
                x = image.getWidth() - 1 - w - center.x;

                newX = (int) (Math.round(x * Math.cos(radian) - y * Math.sin(radian)));
                newY = (int) (Math.round(x * Math.sin(radian) + y * Math.cos(radian)));

                newY = newCenter.y - newY;
                newX = newCenter.x - newX;

                res.setRGB(newX, newY, image.getRGB(w, h));
            }
        return res;
    }

    public BufferedImage rotateBy3ShareMethod() {
        int newX, newY, x, y;
        double tangent;

        BufferedImage res = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int h = 0; h < image.getHeight(); h++)
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
        return res;
    }
}