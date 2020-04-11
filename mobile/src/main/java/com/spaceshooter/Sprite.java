package com.spaceshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends ScreenGameObject {

    protected final double pixelFactor;
    private final Bitmap bitmap;
    private final Matrix matrix = new Matrix();
    protected double rotation;
    protected double speedX;
    protected double speedY;

    protected Sprite(GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        pixelFactor = gameEngine.mPixelFactor;

        height = spriteDrawable.getIntrinsicHeight();
        width = spriteDrawable.getIntrinsicWidth();

        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if ( xPosition > canvas.getWidth()
                || yPosition > canvas.getHeight()
                || xPosition < -width
                || yPosition < -height ) {
            return;
        }

        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor );
        matrix.postTranslate((float) xPosition, (float) yPosition );
        matrix.postRotate((float) rotation, (float) ( xPosition + width / 2), (float) ( yPosition + height / 2));

        canvas.drawBitmap( bitmap, matrix, null);
    }
}
