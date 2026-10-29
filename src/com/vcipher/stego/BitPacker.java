package com.vcipher.stego;

import java.util.Random;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.awt.image.BufferedImage;
import com.vcipher.utils.Coordinates;

public class BitPacker {

    public final int BYTE_IN_BITS = 8;
    public final int BITS_PER_PIXEL = 3;


    public int getCapacity(BufferedImage image) {
        return image.getHeight() * image.getWidth() * BITS_PER_PIXEL / BYTE_IN_BITS;
    }

    // Function => To convert integer data of size number of bits to an array of
    // bits
    public int[] getBits(int data, int size) {
        int[] bits = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            bits[size - 1 - i] = (data >> i) & 1;
        }
        return bits;
    }

    // Function => To convert String data of size number of bits to an array of bits
    public int[] getBits(String data) {
        int size = data.length();
        int[] bits = new int[size * BYTE_IN_BITS];
        for (int j = 0, i = 0; i < size; i++) {
            int[] chBits = getBits((int) data.charAt(i), BYTE_IN_BITS);
            System.arraycopy(chBits, 0, bits, j, chBits.length);
            j += 8;
        }
        return bits;
    }

    // Function => To convert an array of bits to an integer of size number of bits
    public int restore(int[] data, int size) {
        int res = 0;
        for (int i = 0; i < size; i++)
            res = res * 2 + data[i];
        return res;
    }

    // Function => To convert an array of bits to a string
    public String restore(int[] data) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0, i = 0; i < data.length / 8; i++) {
            int[] chBits = new int[BYTE_IN_BITS];
            System.arraycopy(data, j, chBits, 0, BYTE_IN_BITS);
            sb.append((char) restore(chBits, BYTE_IN_BITS));
            j += 8;
        }
        return sb.toString();
    }

    public int bitsEncode(int original, int R, int G, int B) {
        int red = (original >> 16) & 255, green = (original >> 8) & 255, blue = original & 255;
        red = (red & ~1) | (R & 1);
        green = (green & ~1) | (G & 1);
        blue = (blue & ~1) | (B & 1);
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }

    public Coordinates[] shuffle(String password, int width, int height) throws Exception {

        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] hash = hasher.digest(password.getBytes());

        long seed = ByteBuffer.wrap(hash).getLong();
        Random rng = new Random(seed);

        Coordinates[] coordinates = Coordinates.getCoordinates(width, height);

        for (int i = coordinates.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            Coordinates coordinate = coordinates[j];
            coordinates[j] = coordinates[i];
            coordinates[i] = coordinate;
        }

        return coordinates;
    }
}