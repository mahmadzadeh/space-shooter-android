package com.spaceshooter;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private static final long TIME_BETWEEN_BULLETS = 250;
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;

    private List<Bullet> bullets = new ArrayList<Bullet>();

    private double positionX;
    private double positionY;
    private int mMaxX;
    private int mMaxY;
    private double mSpeedFactor;
    private long mTimeSinceLastFire;

    public Player(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship);
        mSpeedFactor = mPixelFactor * 100d / 1000d;

        mMaxX = gameEngine.mWidth - mImageWidth;
        mMaxY = gameEngine.mHeight - mImageHeight;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    @Override
    public void startGame() {
        mPositionX = mMaxX / 2;
        mPositionY = mMaxY / 2;
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
            b.init(this, mPositionX + mImageWidth / 2, mPositionY);
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        } else {
            mTimeSinceLastFire += elapsedMillis;
        }
    }

    private void updatePosition(long elapsedTime, InputController inputController) {
        mPositionX += mSpeedFactor * inputController.mHorizontalFactor * elapsedTime;
        if (mPositionX < 0) {
            mPositionX = 0;
        }
        if (mPositionX > mMaxX) {
            mPositionX = mMaxX;
        }
        mPositionY += mSpeedFactor * inputController.mVerticalFactor * elapsedTime;
        if (mPositionY < 0) {
            mPositionY = 0;
        }
        if (mPositionY > mMaxY) {
            mPositionY = mMaxY;
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
}
