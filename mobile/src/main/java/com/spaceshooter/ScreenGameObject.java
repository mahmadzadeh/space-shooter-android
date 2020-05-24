package com.spaceshooter;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    private final Rect boundingRect;
    protected final Position position;

    protected int height;
    protected int width;

    protected ScreenGameObject( Rect boundingRect ) {
        this.boundingRect = boundingRect;
        this.position = new Position( boundingRect.left, boundingRect.top );
    }

    public boolean checkCollision( ScreenGameObject gameObject ) {
        return checkRectangularCollision( gameObject );
    }

    public void onCollision( GameEngine gameEngine, ScreenGameObject sgo ) {
    }

    public void onPostUpdate( ) {
        boundingRect.set( ( int ) position.getY(), ( int ) position.getY(), ( int ) position.getY() + width, ( int ) position.getY() + height );
    }

    public Rect getBoundingRect( ) {
        return new Rect( boundingRect );
    }

    @Override
    public String toString( ) {
        return "ScreenGameObject{" +
                "boundingRect=" + boundingRect +
                ", position=" + position +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    private boolean checkRectangularCollision( ScreenGameObject other ) {
        return Rect.intersects( boundingRect, other.boundingRect );
    }
}
