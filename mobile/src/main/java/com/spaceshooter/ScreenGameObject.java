package com.spaceshooter;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    public Rect mBoundingRect = new Rect(-1, -1, -1, -1);
    protected double mX;
    protected double mY;
    protected int mHeight;
    protected int mWidth;

    public boolean checkCollision(ScreenGameObject gameObject) {
        return checkRectangularCollision(gameObject);
    }

    private boolean checkRectangularCollision(ScreenGameObject other) {
        return Rect.intersects(mBoundingRect, other.mBoundingRect);
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo) {
    }

    public void onPostUpdate(GameEngine gameEngine) {
        mBoundingRect.set(
                (int) mX,
                (int) mY,
                (int) mX + mWidth,
                (int) mY + mHeight);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
