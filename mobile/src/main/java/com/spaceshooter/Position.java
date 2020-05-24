package com.spaceshooter;


/**
 * Represents position of an object on screen
 */
public class Position {

    private double x;
    private double y;

    public Position( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public void update( double newX, double newY ) {
        x = newX;
        y = newY;
    }

    public double getX( ) {
        return x;
    }

    public double getY( ) {
        return y;
    }

    @Override
    public String toString( ) {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
