package com.spaceshooter;


import android.graphics.Bitmap;

public class Asteroid extends Sprite {

    private final AsteroidPool asteroidPool;

    private final double speed;

    private double rotationSpeed;

    public Asteroid( Bitmap bitmap, GameUiParameters parameters, AsteroidPool asteroidPool ) {
        super( bitmap, parameters );

        speed = 200d * parameters.getPixelFactor() / 1000d;

        this.asteroidPool = asteroidPool;
    }

    public static double getAngle( ) {
        return Math.toRadians( Math.random() * 30 );
    }

    @Override
    public void startGame( ) {
    }

    @Override
    public void onUpdate( long elapsedMillis, GameEngine gameEngine ) {
        position.update( position.getX() + speedX * elapsedMillis, position.getY() + speedY * elapsedMillis );

//        xPosition += speedX * elapsedMillis;
//        yPosition += speedY * elapsedMillis;

        rotation += rotationSpeed * elapsedMillis;
        if ( rotation > 360 ) {
            rotation = 0;
        } else if ( rotation < 0 ) {
            rotation = 360;
        }

        if ( isOutsideScreen( gameEngine ) ) {
            gameEngine.removeGameObject( this );
            asteroidPool.returnToPool( this );
        }
    }

    public void init( GameEngine gameEngine ) {
        double angle = getAngle();
        speedX = speed * Math.sin( angle );
        speedY = speed * Math.cos( angle );

        position.update( gameEngine.random.nextInt( gameEngine.width / 2 ) + gameEngine.width / 4, -height  );

        rotationSpeed = angle * ( 180d / Math.PI ) / 250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt( 360 );
    }

    public void onCollision( GameEngine gameEngine, ScreenGameObject otherObject ) {
        if ( otherObject instanceof Player ) {
            // Remove both from the game (and return them to their pools)
            removeObject( gameEngine );
            Player a = ( Player ) otherObject;
            a.removeObject( gameEngine );
        } else if ( otherObject instanceof Bullet ) {
            removeObject( gameEngine );
            Bullet a = ( Bullet ) otherObject;
            a.removeObject( gameEngine );
        }
    }

    public void removeObject( GameEngine gameEngine ) {
        gameEngine.removeGameObject( this );
        asteroidPool.returnToPool( this );
    }

    private boolean isOutsideScreen( GameEngine gameEngine ) {
        return position.getY() > gameEngine.height;
    }

}