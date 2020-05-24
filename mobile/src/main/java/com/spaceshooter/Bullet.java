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
        position.update( position.getX(), position.getY() + ( speedFactor * elapsedMillis ) );

        if ( position.getY() < -height ) {
            gameEngine.removeGameObject( this );
            player.releaseBullet( this );
        }
    }

    public void init( Player parent, double positionX, double positionY ) {
        position.update( positionX - ( width / 2 ), positionY - ( height / 2 ) );
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
