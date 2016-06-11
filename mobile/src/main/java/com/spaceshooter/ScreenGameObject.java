package com.spaceshooter;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    protected double mX;
    protected double mY;

    protected int mHeight;
    protected int mWidth;

    public Rect mBoundingRect = new Rect(-1, -1, -1, -1);

    public boolean checkCollision(ScreenGameObject gameObject) {

        return
                new Rect(
                        (int)this.mX,
                        (int)this.mY,
                        (int)this.mX+this.mWidth,
                        (int)this.mY+this.mHeight)
                        .intersect(
                                new Rect(
                                        (int)gameObject.mX,
                                        (int)gameObject.mY,
                                        (int)(gameObject.mX+gameObject.mWidth),
                                        (int)(gameObject.mX+gameObject.mWidth)
                                        ));
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo) {
    }
}
