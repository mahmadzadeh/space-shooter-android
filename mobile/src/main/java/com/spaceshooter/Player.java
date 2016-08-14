package com.spaceshooter;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private static final long TIME_BETWEEN_BULLETS = 250;
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;

    private List<Bullet> bullets = new ArrayList<Bullet>();

    private int mMaxX;
    private int mMaxY;
    private double mSpeedFactor;
    private long mTimeSinceLastFire;

    public Player(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship);
        mSpeedFactor = mPixelFactor * 100d / 1000d;

        mMaxX = gameEngine.mWidth - mWidth;
        mMaxY = gameEngine.mHeight - mHeight;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    @Override
    public void startGame() {
        mX = mMaxX / 2;
        mY = mMaxY / 2;
    }

    @Override
    public void onUpdate(long elapsedTime, GameEngine gameEngine) {
        updatePosition(elapsedTime, gameEngine.inputController);
        checkFiring(elapsedTime, gameEngine);
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.inputController.mIsFiring && mTimeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet b = getBullet();
            if (b == null) {
                return;
            }
            b.init(this, mX + mWidth / 2, mY);
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        } else {
            mTimeSinceLastFire += elapsedMillis;
        }
    }

    private void updatePosition(long elapsedTime, InputController inputController) {
        mX += mSpeedFactor * inputController.mHorizontalFactor * elapsedTime;
        if (mX < 0) {
            mX = 0;
        }
        if (mX > mMaxX) {
            mX = mMaxX;
        }
        mY += mSpeedFactor * inputController.mVerticalFactor * elapsedTime;
        if (mY < 0) {
            mY = 0;
        }
        if (mY > mMaxY) {
            mY = mMaxY;
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    public void releaseBullet(Bullet b) {
        bullets.add(b);
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            gameEngine.removeGameObject(this);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.stopGame();
        }
    }

    public void removeObject(GameEngine gameEngine) {
    }
}
