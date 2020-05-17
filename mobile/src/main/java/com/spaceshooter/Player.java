package com.spaceshooter;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private static final long TIME_BETWEEN_BULLETS = 250;

    private List<Bullet> bullets;

    private int maxX;
    private int maxY;
    private double speedFactor;
    private long timeSinceLastFire;

    public Player( Bitmap bitmap, List<Bullet> bullets, GameUiParameters parameters ) {
        super(  bitmap, parameters );
        this.speedFactor = parameters .getPixelFactor() * 100d / 1000d;

        this.maxX = parameters.getGameEngineDimension().getGameEngineWidth() - width;
        this.maxY = parameters.getGameEngineDimension().getGameEngineHeight() - height;
        this.bullets = bullets;
    }


    @Override
    public void startGame() {
        xPosition = maxX / 2;
        yPosition = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedTime, GameEngine gameEngine) {

        updatePosition(elapsedTime, gameEngine.inputController);

        checkFiring(elapsedTime, gameEngine);
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.inputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet b = getBullet();
            if (b == null) {
                return;
            }
            b.init(this, xPosition + width / 2, yPosition );
            gameEngine.addGameObject(b);
            timeSinceLastFire = 0;
        } else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void updatePosition(long elapsedTime, InputController inputController) {
        xPosition += speedFactor * inputController.horizontalFactor * elapsedTime;
        if ( xPosition < 0) {
            xPosition = 0;
        }
        if ( xPosition > maxX ) {
            xPosition = maxX;
        }
        yPosition += speedFactor * inputController.verticalFactor * elapsedTime;
        if ( yPosition < 0) {
            yPosition = 0;
        }
        if ( yPosition > maxY ) {
            yPosition = maxY;
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    public void releaseBullet(Bullet b) {
        bullets.add(b);
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            gameEngine.removeGameObject(this);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.stopGame();
        }
    }

    public void removeObject(GameEngine gameEngine) {
    }
}
