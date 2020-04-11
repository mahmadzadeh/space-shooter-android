package com.spaceshooter;


public class Asteroid extends Sprite {

    private final AsteroidPool asteroidPool;

    private final double mSpeed;

    private double mRotationSpeed;

    public Asteroid( AsteroidPool asteroidPool, GameEngine gameEngine) {
        super(gameEngine, R.drawable.a10000);
        mSpeed = 200d * pixelFactor / 1000d;
        this.asteroidPool = asteroidPool;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        xPosition += speedX * elapsedMillis;
        yPosition += speedY * elapsedMillis;
        rotation += mRotationSpeed * elapsedMillis;
        if ( rotation > 360) {
            rotation = 0;
        } else if ( rotation < 0) {
            rotation = 360;
        }

        if (isOutsideScreen(gameEngine)) {
            gameEngine.removeGameObject(this);
            asteroidPool.returnToPool(this);
        }
    }

    public void init(GameEngine gameEngine) {
        double angle = getAngle();
        speedX = mSpeed * Math.sin(angle);
        speedY = mSpeed * Math.cos(angle);

        xPosition = gameEngine.mRandom.nextInt(gameEngine.mWidth / 2) + gameEngine.mWidth / 4;
        yPosition = -height;

        mRotationSpeed = angle * (180d / Math.PI) / 250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.mRandom.nextInt(360);
    }

    public static double getAngle( ) {
        return    Math.toRadians(Math.random() * 30);
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Player) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Player a = (Player) otherObject;
            a.removeObject(gameEngine);
        } else if (otherObject instanceof Bullet) {
            removeObject(gameEngine);
            Bullet a = (Bullet) otherObject;
            a.removeObject(gameEngine);
        }
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        asteroidPool.returnToPool(this);
    }

    private boolean isOutsideScreen(GameEngine gameEngine) {
        return yPosition > gameEngine.mHeight;
    }
}