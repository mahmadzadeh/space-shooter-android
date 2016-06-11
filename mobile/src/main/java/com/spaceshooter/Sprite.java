package com.spaceshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends ScreenGameObject {

    protected double mRotation;

    protected final double mPixelFactor;

    private final Bitmap mBitmap;

    private final Matrix mMatrix = new Matrix();

    protected Sprite(GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        mPixelFactor = gameEngine.mPixelFactor;

        mHeight = (int) (spriteDrawable.getIntrinsicHeight()*mPixelFactor);
        mWidth = (int) (spriteDrawable.getIntrinsicWidth()*mPixelFactor);

        mBitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mX > canvas.getWidth()
                || mY > canvas.getHeight()
                || mX < -mWidth
                || mY < -mHeight) {
            return;
        }

        mMatrix.reset();
        mMatrix.postScale((float) mPixelFactor, (float) mPixelFactor);
        mMatrix.postTranslate((float) mX, (float) mY);
        mMatrix.postRotate((float) mRotation, (float) (mX + mWidth/2), (float) (mY + mHeight/2));

        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
