package com.spaceshooter;


public class Asteroid extends Sprite {

    private final GameController mController;

    private final double mSpeed;

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

        if (isOutsideScreen(gameEngine)) {
            gameEngine.removeGameObject(this);
            mController.returnToPool(this);
        }
    }

    public void init(GameEngine gameEngine) {
        double angle = gameEngine.mRandom.nextDouble()*Math.PI/3d-Math.PI/6d;
        mSpeedX = mSpeed * Math.sin(angle);
        mSpeedY = mSpeed * Math.cos(angle);

        mX = gameEngine.mRandom.nextInt(gameEngine.mWidth/2)+gameEngine.mWidth/4;
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
        } else if ( otherObject instanceof Bullet ) {
            removeObject(gameEngine);
            Bullet a = (Bullet) otherObject;
            a.removeObject(gameEngine);
        }
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }

    private boolean isOutsideScreen(GameEngine gameEngine) {
        return mY > gameEngine.mHeight;
    }
}