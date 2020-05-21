package com.spaceshooter;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    private final Rect boundingRect;

    protected double xPosition;
    protected double yPosition;

    protected int height;
    protected int width;

    protected ScreenGameObject( Rect boundingRect ) {
        this.boundingRect = boundingRect;
    }

    public boolean checkCollision( ScreenGameObject gameObject ) {
        return checkRectangularCollision( gameObject );
    }

    public void onCollision( GameEngine gameEngine, ScreenGameObject sgo ) {
    }

    public void onPostUpdate( ) {
        boundingRect.set( ( int ) xPosition, ( int ) yPosition, ( int ) xPosition + width, ( int ) yPosition + height );
    }

    public Rect getBoundingRect( ) {
        return new Rect( boundingRect );
    }

    @Override
    public String toString( ) {
        return "ScreenGameObject {" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", height=" + height +
                ", width=" + width +
                ", boundingRect=" + boundingRect +
                '}';
    }

    private boolean checkRectangularCollision( ScreenGameObject other ) {
        return Rect.intersects( boundingRect, other.boundingRect );
    }
}
