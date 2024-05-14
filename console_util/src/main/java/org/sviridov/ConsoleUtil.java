package org.sviridov;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public final class ConsoleUtil {
    private ConsoleUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Получение строки из консоли.
     * @param msg сообщение
     * @param filter фильтр для проверки
     * @return полученная строка
     */
    public static String getStringValue(final String msg, final String filter) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        while (
                value.isBlank() || !value.matches(filter)
                || isIncorrectFileName(value)
        ) {
            System.out.println("Ошибка. " + msg);
            value = scanner.nextLine();
        }
        return value;
    }

    /**
     * Получение числа из консоли.
     * @param msg сообщение
     * @return полученное число
     */
    public static int getIntegerValue(final String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean isInteger = false;
        while (!isInteger) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                isInteger = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Введите число");
            }
        }
        return value;
    }

    /**
     * Получение числа из множества доступных.
     * @param msg сообщение
     * @param correctValues множетво доступных чисел
     * @return введенное число
     */
    public static int getIntegerValue(
            final String msg,
            final Set<Integer> correctValues
    ) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean isInteger = false;
        while (!isInteger) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                isInteger = correctValues.contains(value);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Введите число");
            }
        }
        return value;
    }

    /**
     * Проверка на корректность имени.
     * @param fileName имя файла
     * @return корректный ли файл
     */
    public static boolean isIncorrectFileName(final String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return !(file.canRead() && file.canWrite());
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return true;
            }
            return false;
        }
    }
}
