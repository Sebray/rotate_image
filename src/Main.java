import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Реализовать поворот изборажения на произвольный градус
public class Main {
    public static void main(String[] args) {
        String fileName = ConsoleUtil.getStringValue("Введите путь изображения");
        String resultFileName = ConsoleUtil.getStringValue("Введите название измененного изображения");
        int angle = ConsoleUtil.getIntegerValue("Введите угол поворотоа");
        BufferedImage image;

        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage resImage;
        Rotation rotation = new Rotation(image, angle);
        if (Math.abs(angle) >= 150 && Math.abs(angle) <= 210)
            resImage = rotation.rotateBySimpleMethod();
        else
            resImage = rotation.rotateBy3ShareMethod();

        Smoothing.smoothOut(resImage);

        try {
            ImageIO.write(resImage, "png", new File(resultFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}