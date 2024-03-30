import java.util.Scanner;

public class ConsoleUtil {
    private ConsoleUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static String getStringValue(String msg){
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        while (value.isBlank()){
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
}