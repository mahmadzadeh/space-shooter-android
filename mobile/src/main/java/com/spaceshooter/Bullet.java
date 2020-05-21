package com.spaceshooter;


import android.graphics.Bitmap;

public class Bullet extends Sprite {
    private double speedFactor;

    private Player player;

    public Bullet( Bitmap bitmap, GameUiParameters parameters ) {
        super( bitmap, parameters );

        speedFactor = parameters.getPixelFactor() * -300d / 1000d;
    }

    @Override
    public void startGame( ) {
    }

    @Override
    public void onUpdate( long elapsedMillis, GameEngine gameEngine ) {
        yPosition += speedFactor * elapsedMillis;
        if ( yPosition < -height ) {
            gameEngine.removeGameObject( this );
            player.releaseBullet( this );
        }
    }

    public void init( Player parent, double positionX, double positionY ) {
        xPosition = positionX - width / 2;
        yPosition = positionY - height / 2;
        player = parent;
    }

    public void onCollision( GameEngine gameEngine, ScreenGameObject otherObject ) {
        if ( otherObject instanceof Asteroid ) {
            removeObject( gameEngine );
            Asteroid a = ( Asteroid ) otherObject;
            a.removeObject( gameEngine );
        }
    }

    public void removeObject( GameEngine gameEngine ) {
        gameEngine.removeGameObject( this );
        player.releaseBullet( this );
    }

}
