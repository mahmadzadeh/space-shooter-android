package com.spaceshooter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Bullet extends GameObject {
    private final ImageView mImageView;
    private final double mImageHeight;
    private final double mImageWidth;

    private double mSpeedFactor;

    private double mPositionX;
    private double mPositionY;
    private Player mParent;

    public Bullet(View view, double pixelFactor) {

        Context c = view.getContext();

        mSpeedFactor = pixelFactor * -300d / 1000d;

        mImageView = new ImageView(c);
        Drawable bulletDrawable = c.getResources().getDrawable(R.drawable.bullet);

        mImageHeight = bulletDrawable.getIntrinsicHeight() * pixelFactor;
        mImageWidth = bulletDrawable.getIntrinsicWidth() * pixelFactor;

        mImageView.setLayoutParams(new ViewGroup.LayoutParams(
                (int) (mImageWidth),
                (int) (mImageHeight)));
        mImageView.setImageDrawable(bulletDrawable);

        mImageView.setVisibility(View.GONE);

        ((FrameLayout) view).addView(mImageView);

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedTime, GameEngine gameEngine) {
        mPositionY += mSpeedFactor * elapsedTime;
        if (mPositionY < -mImageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            mParent.releaseBullet(this);
        }
    }

    @Override
    public void onDraw() {

    }

    @Override
    protected void onRemovedFromGameUiThread() {

    }

    @Override
    protected void onAddedToGameUiThread() {

    }

    public void init(Player player, double positionX, double positionY) {
        mPositionX = positionX - mImageWidth/2;
        mPositionY = positionY - mImageHeight/2;
        mParent = player;
    }
}
