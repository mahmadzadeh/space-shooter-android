package com.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public abstract class Sprite extends ScreenGameObject {

    protected final double pixelFactor;
    protected double rotation;
    protected double speedX;
    protected double speedY;

    private final Bitmap bitmap;
    private final Matrix matrix = new Matrix();

    protected Sprite( Bitmap bitmap, GameUiParameters parameters ) {
        super( new Rect( -1, -1, -1, -1 ) );

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
