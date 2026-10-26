package com.vcipher.utils;

public class Coordinates {
    int x;
    int y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates[] getCoordinates(int width, int height) {
        Coordinates[] coordinates = new Coordinates[width*height];
        for (int s = 0, i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Coordinates coordinate = new Coordinates(i, j);
                coordinates[s++] = coordinate; 
            }
        }
        return coordinates;
    }
}
