package com.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class Sprite extends ScreenGameObject {

    protected final double pixelFactor;
    private final Bitmap bitmap;
    private final Matrix matrix = new Matrix();
    protected double rotation;
    protected double speedX;
    protected double speedY;

    protected Sprite( Bitmap bitmap, GameUiParameters parameters ) {
        this.bitmap = bitmap;
        this.pixelFactor = parameters.getPixelFactor();
        this.height = parameters.getDrawableHeight();
        this.width = parameters.getDrawableWidth();
    }

    @Override
    public void onDraw( Canvas canvas ) {
        if ( xPosition > canvas.getWidth()
                || yPosition > canvas.getHeight()
                || xPosition < -width
                || yPosition < -height ) {
            return;
        }

        matrix.reset();
        matrix.postScale( ( float ) pixelFactor, ( float ) pixelFactor );
        matrix.postTranslate( ( float ) xPosition, ( float ) yPosition );
        matrix.postRotate( ( float ) rotation, ( float ) ( xPosition + width / 2 ), ( float ) ( yPosition + height / 2 ) );

        canvas.drawBitmap( bitmap, matrix, null );
    }
}
