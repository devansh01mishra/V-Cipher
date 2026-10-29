package com.vcipher.stego;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.vcipher.utils.Coordinates;

public class SteganoManager {

    public BufferedImage encodeMessage(String message, String password, BufferedImage image) {

        BitPacker packer = new BitPacker();
        int[] messageInBits = packer.getBits(message), messageLengthInBits = packer.getBits(messageInBits.length, Integer.SIZE);
        long imageCapacityBits = (long) image.getWidth() * image.getHeight() * 3;
        if ((long) messageInBits.length + messageLengthInBits.length > imageCapacityBits) return null;
        int[] bitStream = new int[messageInBits.length + messageLengthInBits.length];
        System.arraycopy(messageLengthInBits, 0, bitStream, 0, messageLengthInBits.length);
        System.arraycopy(messageInBits, 0, bitStream, messageLengthInBits.length, messageInBits.length);
        
        BufferedImage messageImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        try {
            Coordinates[] coordinates = packer.shuffle(password, image.getWidth(), image.getHeight());
            int bitsStuffed = 0;
            for (Coordinates coordinate : coordinates) {
                int currentPixel = image.getRGB(coordinate.x, coordinate.y), modifiedPixel = currentPixel;
                int R = 0, G = 0, B = 0;
                if(bitsStuffed < bitStream.length) R = bitStream[bitsStuffed++]; 
                if(bitsStuffed < bitStream.length) G = bitStream[bitsStuffed++]; 
                if(bitsStuffed < bitStream.length) B = bitStream[bitsStuffed++]; 
                modifiedPixel = packer.bitsEncode(currentPixel, R, G, B);
                messageImage.setRGB(coordinate.x, coordinate.y, modifiedPixel);
            }
        } catch (Exception e) {
            // Todo: handle exception
        }

        return messageImage;
    }

    public String decodeMessage(BufferedImage image, String password) {
        BitPacker packer = new BitPacker();
        Coordinates[] coordinates;
        try {
            coordinates = packer.shuffle(password, image.getWidth(), image.getHeight());
        } catch (Exception e) {
            throw new IllegalStateException("Could not generate pixel traversal order.", e);
        }

        int[] messageLengthInBits = new int[Integer.SIZE];
        for (int bitIndex = 0; bitIndex < messageLengthInBits.length; bitIndex++) {
            messageLengthInBits[bitIndex] = getChannelBit(image, coordinates, bitIndex);
        }

        int messageLength = packer.restore(messageLengthInBits, Integer.SIZE);
        long availableMessageBits = (long) coordinates.length * 3 - Integer.SIZE;
        if (messageLength < 0 || messageLength > availableMessageBits) {
            throw new IllegalArgumentException("Image does not contain a valid encoded message length.");
        }

        int[] messageInBits = new int[messageLength];
        for (int bitIndex = 0; bitIndex < messageLength; bitIndex++) {
            messageInBits[bitIndex] = getChannelBit(image, coordinates, Integer.SIZE + bitIndex);
        }
        return packer.restore(messageInBits);
    }

    private int getChannelBit(BufferedImage image, Coordinates[] coordinates, int bitIndex) {
        Coordinates coordinate = coordinates[bitIndex / 3];
        int pixel = image.getRGB(coordinate.x, coordinate.y);
        int channel = bitIndex % 3;
        int value = channel == 0 ? pixel >> 16 : channel == 1 ? pixel >> 8 : pixel;
        return value & 1;
    }
}