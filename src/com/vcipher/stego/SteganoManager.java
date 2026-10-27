package com.vcipher.stego;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.vcipher.utils.Coordinates;

public class SteganoManager {

    public BufferedImage encodeMessage(String message, String password, BufferedImage image) {

        BitPacker packer = new BitPacker();
        int[] messageInBits = packer.getBits(message), messageLengthInBits = packer.getBits(messageInBits.length, Integer.SIZE);
        int imageCapacity = packer.getCapacity(image);
    
        if( messageInBits.length + messageLengthInBits.length > imageCapacity )  return null;
        
        BufferedImage messageImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        try {
            Coordinates[] coordinates = packer.shuffle(password, image.getWidth(), image.getHeight());
    
            int bitsStuffed = 0;
            for (Coordinates coordinate : coordinates) {
                int currentPixel = image.getRGB(coordinate.x, coordinate.y);
                int modifiedPixel = currentPixel;
                int R = 0, G = 0, B = 0;
                if(bitsStuffed < messageLengthInBits.length) {
                    R = messageLengthInBits[bitsStuffed++];
                    G = messageLengthInBits[bitsStuffed++];
                    if(bitsStuffed < messageLengthInBits.length) B = messageLengthInBits[bitsStuffed++];
                    modifiedPixel = packer.bitEncode(currentPixel, R, G, B);
                } else if (bitsStuffed < messageInBits.length + messageLengthInBits.length) {                    
                    R = messageInBits[bitsStuffed++];
                    G = messageInBits[bitsStuffed++];
                    if(bitsStuffed < messageInBits.length + messageLengthInBits.length) B = messageInBits[bitsStuffed++];
                    modifiedPixel = packer.bitEncode(currentPixel, R, G, B);
                } 
                messageImage.setRGB(coordinate.x, coordinate.y, modifiedPixel);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        return messageImage;
    }

    public String decodeMessage(BufferedImage image, String password) {
        String message = "";
        try {
            Coordinates[] coordinates = packer.shuffle(password, image.getWidth(), image.getHeight());
            int bitStuffed = 0;
            int[] messageLengthInBits = new int[Integer.SIZE];
            for (int i = 0; i < messageLengthInBits.length; i++) {
                Coordinates coordinate = coordinates[bitStuffed];
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return message;
    }
}