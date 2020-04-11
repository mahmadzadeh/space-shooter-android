package com.spaceshooter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ParallaxBackground extends GameObject {

    private final Rect mSrcRect = new Rect();
    private final Rect mDstRect = new Rect();

    private final double mImageHeight;
    private final double mImageWidth;
    private final Bitmap mBitmap;
    private final double mSpeedY;
    private final double mScreenHeight;
    private final double mScreenWidth;

    private final Matrix mMatrix = new Matrix();
    private final double mPixelFactor;
    private final double mTargetWidth;

    protected double mPositionY;
    private Paint mPaint = new Paint();

    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableResId) {
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableResId);
        mBitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        mPixelFactor = gameEngine.mPixelFactor;
        mSpeedY = speed * mPixelFactor / 1000d;

        mImageHeight = spriteDrawable.getIntrinsicHeight() * mPixelFactor;
        mImageWidth = spriteDrawable.getIntrinsicWidth() * mPixelFactor;

        mScreenHeight = gameEngine.mHeight;
        mScreenWidth = gameEngine.mWidth;

        mTargetWidth = Math.min(mImageWidth, mScreenWidth);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mPositionY += mSpeedY * elapsedMillis;
    }

    @Override
    public void onDraw(Canvas canvas) {
        efficientDraw(canvas);
    }

    private void efficientDraw(Canvas canvas) {
        if (mPositionY < 0) {
            mSrcRect.set(0,
                    (int) (-mPositionY / mPixelFactor),
                    (int) (mTargetWidth / mPixelFactor),
                    (int) ((mScreenHeight - mPositionY) / mPixelFactor));
            mDstRect.set(0,
                    0,
                    (int) mTargetWidth,
                    (int) mScreenHeight);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        } else {
            mSrcRect.set(0,
                    0,
                    (int) (mTargetWidth / mPixelFactor),
                    (int) ((mScreenHeight - mPositionY) / mPixelFactor));
            mDstRect.set(0,
                    (int) mPositionY,
                    (int) mTargetWidth,
                    (int) mScreenHeight);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
            // We need to draw the previous block
            mSrcRect.set(0,
                    (int) ((mImageHeight - mPositionY) / mPixelFactor),
                    (int) (mTargetWidth / mPixelFactor),
                    (int) (mImageHeight / mPixelFactor));
            mDstRect.set(0,
                    0,
                    (int) mTargetWidth,
                    (int) mPositionY);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        }

        if (mPositionY > mScreenHeight) {
            mPositionY -= mImageHeight;
        }
    }
}
