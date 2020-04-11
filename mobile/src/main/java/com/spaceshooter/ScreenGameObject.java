package com.spaceshooter;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    public Rect boundingRect = new Rect(-1, -1, -1, -1);
    protected double xPosition;
    protected double yPosition;
    protected int height;
    protected int width;

    public boolean checkCollision(ScreenGameObject gameObject) {
        return checkRectangularCollision(gameObject);
    }

    private boolean checkRectangularCollision(ScreenGameObject other) {
        return Rect.intersects( boundingRect, other.boundingRect );
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo) {
    }

    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) xPosition,
                (int) yPosition,
                (int) xPosition + width,
                (int) yPosition + height );
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
