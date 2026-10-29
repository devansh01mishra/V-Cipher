package com.vcipher.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class FileManager {

    public static String readMessageFile(String filePath) throws FileNotFoundException {
        StringBuilder message = new StringBuilder();
        File messageFile = new File(filePath);
        try (Scanner scan = new Scanner(messageFile, StandardCharsets.UTF_8.name())) {
            boolean firstLine = true;
            while (scan.hasNextLine()) {
                if (!firstLine) {
                    message.append('\n');
                }
                message.append(scan.nextLine());
                firstLine = false;
            }
        }
        return message.toString();
    }

    public BufferedImage readImageFile(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        if (image == null) {
            throw new IOException("The selected file is not a supported image.");
        }
        return image;
    }

    public File writeImage(BufferedImage image, String format) throws IOException {
        String normalizedFormat = normalizeFormat(format);
        return writeImage(image, new File("sealed-image." + normalizedFormat), normalizedFormat);
    }

    public File writeImage(BufferedImage image, File destination, String format) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Image destination cannot be null.");
        }

        String normalizedFormat = normalizeFormat(format);
        if (!ImageIO.write(image, normalizedFormat, destination)) {
            throw new IOException("No image writer is available for format: " + normalizedFormat);
        }
        return destination;
    }

    public File writeMessage(String message, String format) throws IOException {
        String normalizedFormat = normalizeFormat(format);
        return writeMessage(message, new File("secret-message." + normalizedFormat));
    }

    public File writeMessage(String message, File destination) throws IOException {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null.");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Message destination cannot be null.");
        }

        Files.write(destination.toPath(), message.getBytes(StandardCharsets.UTF_8));
        return destination;
    }

    private String normalizeFormat(String format) {
        if (format == null) {
            throw new IllegalArgumentException("File format cannot be null.");
        }

        String normalizedFormat = format.trim().toLowerCase();
        if (normalizedFormat.startsWith(".")) {
            normalizedFormat = normalizedFormat.substring(1);
        }
        if (!normalizedFormat.matches("[a-z0-9]+")) {
            throw new IllegalArgumentException("File format must be a valid extension.");
        }
        return normalizedFormat;
    }
}
