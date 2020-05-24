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
        if ( position.getX() > canvas.getWidth()
                || position.getY() > canvas.getHeight()
                || position.getX()  < -width
                || position.getY() < -height ) {

            return;
        }

        matrix.reset();
        matrix.postScale( ( float ) pixelFactor, ( float ) pixelFactor );
        matrix.postTranslate( ( float ) position.getX(), ( float ) position.getY() );
        matrix.postRotate( ( float ) rotation, ( float ) ( position.getX() + width / 2 ), ( float ) ( position.getY() + height / 2 ) );

        canvas.drawBitmap( bitmap, matrix, null );
    }
}
