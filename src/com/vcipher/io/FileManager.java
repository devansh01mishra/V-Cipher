package com.vcipher.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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

    public File writeImage(BufferedImage image, String format) {
        // Todo : Make an image file writing method
        return null;
    }
    
    public File writeMessage(String message, String format) {
        // Todo : Make a message file writing method
        return null;
    }
}
