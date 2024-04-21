package org.sviridov;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

//Реализовать поворот изборажения на произвольный градус
public class Main {
    public static final String REGULAR = ".+(?=\\\\*)\\\\*[A-Za-z\\_0-9\\.-]+.(png|jpg|jpeg)";
    public static final Integer FIRST = 1;
    public static final Integer SECOND = 2;

    public static void main(String[] args) {
        List<String> names = getFileNames();
        int angle = ConsoleUtil.getIntegerValue("Введите угол поворота");
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(names.get(0)));
        } catch (IOException e) {
            System.out.println("Файл с названием " + names.get(0) + " не может быть прочтен");
        }

        BufferedImage resImage = doRotation(image, angle);
        doSmoothing(resImage);
        writeResultImage(names.get(1), resImage);
    }

    public static List<String> getFileNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add(ConsoleUtil.getStringValue("Введите путь изображения", REGULAR));
        names.add(ConsoleUtil.getStringValue("Введите путь измененного изображения", REGULAR));
        return names;
    }

    public static Set<Integer> initChoices() {
        HashSet<Integer> choices = new HashSet<>();
        choices.add(FIRST);
        choices.add(SECOND);
        return choices;
    }

    public static Integer doChoice() {
        return ConsoleUtil.getIntegerValue(
                "Выберите метод для сглаживания изображения.\n 1 - билинейная интерполяция," +
                        " 2 – ближайший сосед",
                initChoices());
    }

    public static void doSmoothing(BufferedImage resImage) {
        if (Objects.equals(doChoice(), FIRST)) {
            Smoothing.smoothOut(resImage);
        } else {
            Smoothing.smoothOutByNearPixel(resImage);
        }
    }

    public static BufferedImage doRotation(BufferedImage image, Integer angle) {
        Rotation rotation = new Rotation(image, angle);
        if (Math.abs(angle) >= 150 && Math.abs(angle) <= 210)
            return rotation.rotateBySimpleMethod();
        else
            return rotation.rotateBy3ShareMethod();
    }

    public static void writeResultImage(String name, BufferedImage image){
        try {
            ImageIO.write(image, "png", new File(name));
        } catch (IOException e) {
            System.out.println("Файл с названием " + name + " не может быть записан");
        }
    }
}