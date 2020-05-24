package com.spaceshooter;

import android.graphics.Bitmap;

import java.util.List;

public class Player extends Sprite {
    private static final long TIME_BETWEEN_BULLETS = 250;

    private List<Bullet> bullets;

    private int maxX;
    private int maxY;
    private double speedFactor;
    private long timeSinceLastFire;

    public Player( Bitmap bitmap, List<Bullet> bullets, GameUiParameters parameters ) {
        super( bitmap, parameters );
        this.speedFactor = parameters.getPixelFactor() * 100d / 1000d;

        this.maxX = parameters.getGameEngineDimension().getGameEngineWidth() - width;
        this.maxY = parameters.getGameEngineDimension().getGameEngineHeight() - height;
        this.bullets = bullets;
    }


    @Override
    public void startGame( ) {
        position.update( maxX / 2, maxY / 2 );
    }

    @Override
    public void onUpdate( long elapsedTime, GameEngine gameEngine ) {

        updatePosition( elapsedTime, gameEngine.inputController );

        checkFiring( elapsedTime, gameEngine );
    }

    private void checkFiring( long elapsedMillis, GameEngine gameEngine ) {
        if ( gameEngine.inputController.isFiring() && timeSinceLastFire > TIME_BETWEEN_BULLETS ) {
            Bullet b = getBullet();
            if ( b == null ) {
                return;
            }
            b.init( this, position.getX() + width / 2, position.getY() );
            gameEngine.addGameObject( b );
            timeSinceLastFire = 0;
        } else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void updatePosition( long elapsedTime, InputController inputController ) {
        position.update( position.getX() + ( speedFactor * inputController.getHorizontalFactor() * elapsedTime ),
                position.getY() + ( speedFactor * inputController.getVerticalFactor() * elapsedTime ) );

        if ( position.getX() < 0 ) {
            position.update( 0, position.getY() );
        }
        if ( position.getX() > maxX ) {
            position.update( maxX, position.getY() );
        }
        if ( position.getY() < 0 ) {
            position.update( position.getX(), 0 );
        }
        if ( position.getY() > maxY ) {
            position.update( position.getX(), maxY );
        }
    }

    private Bullet getBullet( ) {
        if ( bullets.isEmpty() ) {
            return null;
        }
        return bullets.remove( 0 );
    }

    public void releaseBullet( Bullet b ) {
        bullets.add( b );
    }

    public void onCollision( GameEngine gameEngine, ScreenGameObject otherObject ) {
        if ( otherObject instanceof Asteroid ) {
            gameEngine.removeGameObject( this );
            Asteroid a = ( Asteroid ) otherObject;
            a.removeObject( gameEngine );
            gameEngine.stopGame();
        }
    }

    public void removeObject( GameEngine gameEngine ) {
    }
}
