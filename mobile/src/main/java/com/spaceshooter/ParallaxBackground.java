package com.spaceshooter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class ParallaxBackground extends GameObject {

    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();

    private final double imageHeight;
    private final double imageWidth;
    private final Bitmap bitmap;
    private final double speedY;
    private final double screenHeight;
    private final double screenWidth;


    private final double pixelFactor;
    private final double targetWidth;
    protected double positionY;

    public ParallaxBackground( Bitmap backgroundBitmap, int speed, GameUiParameters parameters) {

        this.bitmap = backgroundBitmap;

        this.pixelFactor = parameters.getPixelFactor();
        this.speedY = speed * this.pixelFactor / 1000d;

        this.imageHeight = parameters.getDrawableHeight() * this.pixelFactor;
        this.imageWidth = parameters.getDrawableWidth()* this.pixelFactor;

        this.screenHeight = parameters.getGameEngineDimension().getGameEngineHeight();
        this.screenWidth = parameters.getGameEngineDimension().getGameEngineWidth();

        this.targetWidth = Math.min( imageWidth, screenWidth );
    }


    @Override
    public void onUpdate( long elapsedMillis, GameEngine gameEngine ) {
        positionY += speedY * elapsedMillis;
    }

    @Override
    public void onDraw( Canvas canvas ) {
        efficientDraw( canvas );
    }

    @Override
    public void startGame( ) {
        return; // do nothing
    }

    /**
     * since background image does not have a bounding box there is nothing to update after
     * position change.
     */
    @Override
    public void onPostUpdate( ) {
        return;//do nothing
    }

    private void efficientDraw( Canvas canvas ) {
        if ( positionY < 0 ) {
            srcRect.set( 0,
                    ( int ) ( -positionY / pixelFactor ),
                    ( int ) ( targetWidth / pixelFactor ),
                    ( int ) ( ( screenHeight - positionY ) / pixelFactor ) );
            dstRect.set( 0,
                    0,
                    ( int ) targetWidth,
                    ( int ) screenHeight );
            canvas.drawBitmap( bitmap, srcRect, dstRect, null );
        } else {
            srcRect.set( 0,
                    0,
                    ( int ) ( targetWidth / pixelFactor ),
                    ( int ) ( ( screenHeight - positionY ) / pixelFactor ) );
            dstRect.set( 0,
                    ( int ) positionY,
                    ( int ) targetWidth,
                    ( int ) screenHeight );
            canvas.drawBitmap( bitmap, srcRect, dstRect, null );
            // We need to draw the previous block
            srcRect.set( 0,
                    ( int ) ( ( imageHeight - positionY ) / pixelFactor ),
                    ( int ) ( targetWidth / pixelFactor ),
                    ( int ) ( imageHeight / pixelFactor ) );
            dstRect.set( 0,
                    0,
                    ( int ) targetWidth,
                    ( int ) positionY );
            canvas.drawBitmap( bitmap, srcRect, dstRect, null );
        }

        if ( positionY > screenHeight ) {
            positionY -= imageHeight;
        }
    }
}
