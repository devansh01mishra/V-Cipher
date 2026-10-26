package com.vcipher.stego;

import java.util.Random;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import com.vcipher.utils.Coordinates;


public class BitPacker {

    public long getCapacity(BufferedImage image) {
        return image.getHeight() * image.getWidth() / 8 + 1;
    }

    public Coordinates[] shuffle(String password, int width, int height) {
        
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] hash = hasher.digest(password.getBytes());

        long seed = ByteBuffer.wrap(hash).getLong();
        Random rng = new Random(seed); 
        
        Coordinates[] coordinates = Coordinates.getCoordinates(width, height);

        for (int i = coordinates.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Coordinates coordinate = coordinates[j];
            coordinates[j] = coordinates[i];
            coordinates[i] = coordinate;
        }

        return coordinates;
    }
}