package com.vcipher.stego;

import java.awt.image.BufferedImage;

import com.vcipher.utils.Coordinates;

public class SteganoManager {

    public long getCapacity(BufferedImage image) {
        return image.getHeight() * image.getWidth() / 8 + 1;
    }

    public BufferedImage encodeMessage(String message, BufferdImage image, Coordinates[] coordinates) {
        long imageCapacity = getCapacity(image);
    }

    public String decodeMessage(BufferedImage image, Coordinates[] coordinates) {
        String message = "";
        return message;
    }
}