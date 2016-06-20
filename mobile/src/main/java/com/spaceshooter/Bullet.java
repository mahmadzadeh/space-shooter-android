package com.spaceshooter;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Bullet extends Sprite  {
    private double mSpeedFactor;

    private Player mParent;

    public Bullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet);
        mSpeedFactor = gameEngine.mPixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mY += mSpeedFactor * elapsedMillis;
        if (mY < -mHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            mParent.releaseBullet(this);
        }
    }

    public void init(Player parent, double positionX, double positionY) {
        mX = positionX - mWidth/2;
        mY = positionY - mHeight/2;
        mParent = parent;
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
        }
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        mParent.releaseBullet(this);
    }

}
