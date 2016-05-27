package com.spaceshooter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private final ImageView ship;
    private final TextView mTextView;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private double positionX;
    private double positionY;
    private double speedFactor;
    private int maxX;
    private int maxY;
    private double mSpeedFactor;
    private View view;

    private long mTimeSinceLastFire;

    private double pixelFactor;
    private long TIME_BETWEEN_BULLETS = 250;

    public Player(View view) {
        this.view = view;
        mTextView = (TextView) view.findViewById(R.id.txt_score);
        pixelFactor = view.getHeight() / 400d;
        maxX = view.getWidth() - view.getPaddingRight() - view.getPaddingRight();
        maxY = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        mSpeedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen

        ship = new ImageView(view.getContext());
        Drawable shipDrawable = view.getContext().getResources().getDrawable(R.drawable.ship);
        ship.setLayoutParams(new ViewGroup.LayoutParams(
                (int) (shipDrawable.getIntrinsicWidth() * pixelFactor),
                (int) (shipDrawable.getIntrinsicHeight() * pixelFactor)));

        ship.setImageDrawable(shipDrawable);
        ((FrameLayout) view).addView(ship);

        maxX -= (shipDrawable.getIntrinsicWidth() * pixelFactor);
        maxY -= (shipDrawable.getIntrinsicHeight() * pixelFactor);

        initBulletPool();
    }

    private void initBulletPool() {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(view, pixelFactor));
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
        checkFiring(elapsedTime, gameEngine);
    }

    private void checkFiring(long elapsedTime, GameEngine gameEngine) {
        if (gameEngine.inputController.mIsFiring && mTimeSinceLastFire < TIME_BETWEEN_BULLETS) {
            Bullet b = getBullet();
            if (b == null) {
                return;
            }

            b.init(this, positionX + ship.getWidth() / 2, positionY);
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        } else {
            mTimeSinceLastFire += elapsedTime;
        }
    }

    private void updatePosition(long elapsedTime, InputController inputController) {
        positionX += speedFactor * inputController.mHorizontalFactor * elapsedTime;

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


    @Override
    public void onDraw() {
        mTextView.setText("[" + (int) (positionX) + "," + (int) (positionY) + "]");
        ship.setTranslationX(maxX / 2);
        ship.setTranslationY(maxY / 2);
    }

    @Override
    protected void onRemovedFromGameUiThread() {

    }

    @Override
    protected void onAddedToGameUiThread() {

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
