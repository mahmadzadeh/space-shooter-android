package com.spaceshooter;


public class Asteroid extends Sprite {

    private final GameController mController;

    private final double mSpeed;
    private double mSpeedX;
    private double mSpeedY;
    private double mRotationSpeed;

    public Asteroid(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.a10000);
        mSpeed = 200d*mPixelFactor/1000d;
        mController = gameController;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;
        mRotation += mRotationSpeed * elapsedMillis;
        if (mRotation > 360) {
            mRotation = 0;
        }
        else if (mRotation < 0) {
            mRotation = 360;
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (mY > gameEngine.mHeight) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            mController.returnToPool(this);
        }
    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.mRandom.nextDouble()*Math.PI/3d-Math.PI/6d;
        mSpeedX = mSpeed * Math.sin(angle);
        mSpeedY = mSpeed * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        mX = gameEngine.mRandom.nextInt(gameEngine.mWidth/2)+gameEngine.mWidth/4;
        // They initialize outside of the screen vertically
        mY = -mHeight;
        mRotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        mRotation = gameEngine.mRandom.nextInt(360);
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Player) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Player a = (Player) otherObject;
            a.removeObject(gameEngine);
        }
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }
}