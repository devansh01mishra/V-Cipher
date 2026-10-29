package com.vcipher.utils;

public class Coordinates {
    public int x;
    public int y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates[] getCoordinates(int width, int height) {
        Coordinates[] coordinates = new Coordinates[width * height];
        for (int s = 0, y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                coordinates[s++] = new Coordinates(x, y);
            }
        }
        return coordinates;
    }
}
