package com.spaceshooter;

public class InputController {

    private double horizontalFactor;
    private double verticalFactor;
    private boolean isFiring;

    public double getHorizontalFactor( ) {
        return horizontalFactor;
    }

    public double getVerticalFactor( ) {
        return verticalFactor;
    }

    public boolean isFiring( ) {
        return isFiring;
    }

    public void setHorizontalFactor( double horizontalFactor ) {
        this.horizontalFactor = horizontalFactor;
    }

    public void setVerticalFactor( double verticalFactor ) {
        this.verticalFactor = verticalFactor;
    }

    public void setFiring( boolean firing ) {
        isFiring = firing;
    }
}
