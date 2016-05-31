package com.spaceshooter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private double positionX;
    private double positionY;
    private int maxX;
    private int maxY;
    private double mSpeedFactor;

    public Player(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship);
        mSpeedFactor = mPixelFactor * 100d / 1000d;

        maxX = gameEngine.mWidth- mImageWidth;
        maxY = gameEngine.mHeight - mImageHeight;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedTime, GameEngine gameEngine) {
        updatePosition(elapsedTime, gameEngine.inputController);
        checkFiring(gameEngine);
    }

    private void checkFiring(GameEngine gameEngine) {
        if (gameEngine.inputController.mIsFiring) {
            Bullet b = getBullet();
            if (b == null) {
                return;
            }

            b.init(this, positionX + mImageWidth/2, positionY);
            gameEngine.addGameObject(b);
        }
    }

    private void updatePosition(long elapsedTime, InputController inputController) {
        positionX += mSpeedFactor * inputController.mHorizontalFactor * elapsedTime;

        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += mSpeedFactor * inputController.mVerticalFactor * elapsedTime;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
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
