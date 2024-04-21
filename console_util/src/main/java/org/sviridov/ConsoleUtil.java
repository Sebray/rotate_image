package org.sviridov;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class ConsoleUtil {
    private ConsoleUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static String getStringValue(String msg, String filter){
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        while (value.isBlank() || !value.matches(filter) || isIncorrectFileName(value)){
            System.out.println("Ошибка. " + msg);
            value = scanner.nextLine();
        }
        return value;
    }

    public static int getIntegerValue(String msg){
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean isInteger = false;
        while(!isInteger) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                isInteger = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Введите число");
            }
        }
        return value;
    }

    public static int getIntegerValue(String msg, Set<Integer> correctValues){
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean isInteger = false;
        while(!isInteger) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                isInteger = correctValues.contains(value);;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Введите число");
            }
        }
        return value;
    }

    public static boolean isIncorrectFileName(String fileName) {
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