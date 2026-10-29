package com.vcipher.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.vcipher.crypto.CryptoManager;
import com.vcipher.stego.SteganoManager;

public class ServiceManager {
    private static final int MESSAGE_LENGTH_HEADER_BITS = Integer.SIZE;
    private static final int BITS_PER_PIXEL = 3;
    private static final int BITS_PER_BYTE = 8;

    private final CryptoManager cryptoManager;
    private final FileManager fileManager;
    private final SteganoManager steganoManager;

    public ServiceManager() {
        this.cryptoManager = new CryptoManager();
        this.fileManager = new FileManager();
        this.steganoManager = new SteganoManager();
    }

    public BufferedImage loadImage(File file) throws IOException {
        return fileManager.readImageFile(requireFile(file).getAbsolutePath());
    }

    public String loadMessage(File file) throws IOException {
        return FileManager.readMessageFile(requireFile(file).getAbsolutePath());
    }

    public long getMessageCapacityBytes(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }

        long availableBits = (long) image.getWidth() * image.getHeight() * BITS_PER_PIXEL
                - MESSAGE_LENGTH_HEADER_BITS;
        return Math.max(0, availableBits / BITS_PER_BYTE);
    }

    public BufferedImage sealMessage(String message, String password, BufferedImage image) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (image == null) {
            throw new IllegalArgumentException("Cover image cannot be null.");
        }

        String encryptedMessage = cryptoManager.encrypt(message, password);
        BufferedImage encodedImage = steganoManager.encodeMessage(encryptedMessage, password, image);
        if (encodedImage == null) {
            throw new ImageCapacityExceededException();
        }
        return encodedImage;
    }

    public String revealMessage(BufferedImage image, String password) throws Exception {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        String encryptedMessage = steganoManager.decodeMessage(image, password);
        return cryptoManager.decrypt(encryptedMessage, password);
    }

    public File saveImage(BufferedImage image, File destination, String format) throws IOException {
        return fileManager.writeImage(image, destination, format);
    }

    public File saveMessage(String message, File destination) throws IOException {
        return fileManager.writeMessage(message, destination);
    }

    private File requireFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }
        return file;
    }

    public static class ImageCapacityExceededException extends Exception {
        private static final long serialVersionUID = 1L;

        private ImageCapacityExceededException() {
            super("The message is too large for this image.");
        }
    }
}
