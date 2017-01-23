package com.spaceshooter;


public class Bullet extends Sprite {
    private double mSpeedFactor;

    private Player mParent;

    public Bullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet);
        mSpeedFactor = gameEngine.mPixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mY += mSpeedFactor * elapsedMillis;
        if (mY < -mHeight) {
            gameEngine.removeGameObject(this);
            mParent.releaseBullet(this);
        }
    }

    public void init(Player parent, double positionX, double positionY) {
        mX = positionX - mWidth / 2;
        mY = positionY - mHeight / 2;
        mParent = parent;
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
        }
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        mParent.releaseBullet(this);
    }

}
