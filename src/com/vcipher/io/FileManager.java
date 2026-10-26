package com.vcipher.io;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileManager {

    public static String readMessageFile(String filePath) throws FileNotFoundException {
        String message = "";
        File messageFile = new File(filePath);
        Scanner scan = new Scanner(messageFile);
        while (scan.hasNextLine()) {
            String next = scan.nextLine();
            message += next;
            if (scan.hasNextLine()) {
                message += "\n";
            }
        }
        scan.close();
        return message;
    }

    public BufferedImage readImageFile(String filePath) throws IOException {
        BufferedImage image = null;
        File imageFile = new File(filePath);
        image = ImageIO.read(imageFile);
        return image;
    }
}
