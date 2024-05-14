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
final class Main {
    /** Регулярное выражение для проверки названия. */
    public static final String REGULAR =
            ".+(?=\\\\*)\\\\*[A-Za-z\\_0-9\\.-]+.(png|jpg|jpeg)";
    /** Действие выбора 1. */
    public static final Integer FIRST = 1;
    /** Действие выбора 2. */
    public static final Integer SECOND = 2;

    private Main() {

    }

    /**
     * Главный метод программы.
     * @param args аргументы
     */
    public static void main(final String[] args) {
        List<String> names = getFileNames();
        int angle = ConsoleUtil.getIntegerValue("Введите угол поворота");
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(names.get(0)));
        } catch (IOException e) {
            System.out.println(
                    "Файл с названием "
                            + names.get(0)
                            + " не может быть прочтен");
        }

        BufferedImage resImage = doRotation(image, angle);
        doSmoothing(resImage);
        writeResultImage(names.get(1), resImage);
    }

    /**
     * Получения названий файлов.
     * @return список названий
     */
    public static List<String> getFileNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add(
                ConsoleUtil.getStringValue(
                        "Введите путь изображения",
                        REGULAR
                )
        );
        names.add(
                ConsoleUtil.getStringValue(
                        "Введите путь измененного изображения",
                        REGULAR
                )
        );
        return names;
    }

    /**
     * Инициализация доступных чисел для выбора.
     * @return множество доступных чисел
     */
    public static Set<Integer> initChoices() {
        HashSet<Integer> choices = new HashSet<>();
        choices.add(FIRST);
        choices.add(SECOND);
        return choices;
    }

    /**
     * Вызов получения числа.
     * @return полученное число
     */
    public static Integer doChoice() {
        return ConsoleUtil.getIntegerValue(
                "Выберите метод для сглаживания изображения.\n"
                        + "1 - билинейная интерполяция,"
                        + " 2 – ближайший сосед",
                initChoices());
    }

    /**
     * Выполнение сглаживания.
     * @param resImage изображение
     */
    public static void doSmoothing(final BufferedImage resImage) {
        if (Objects.equals(doChoice(), FIRST)) {
            Smoothing.smoothOut(resImage);
        } else {
            Smoothing.smoothOutByNearPixel(resImage);
        }
    }

    /**
     * Запись изображения в файл.
     * @param image изображение
     * @param angle угол поворота
     * @return перевернутое изображение
     */
    public static BufferedImage doRotation(
            final BufferedImage image,
            final Integer angle
    ) {
        final int startShareMethod = 150;
        final int endShareMethod = 210;
        Rotation rotation = new Rotation(image, angle);
        if (Math.abs(angle) >= startShareMethod
                && Math.abs(angle) <= endShareMethod) {
            return rotation.rotateBySimpleMethod();
        } else {
            return rotation.rotateBy3ShareMethod();
        }
    }

    /**
     * Запись изображения в файл.
     * @param name название файла
     * @param image изображение
     */
    public static void writeResultImage(
            final String name,
            final BufferedImage image
    ) {
        try {
            ImageIO.write(image, "png", new File(name));
        } catch (IOException e) {
            System.out.println(
                    "Файл с названием " + name + " не может быть записан"
            );
        }
    }
}
