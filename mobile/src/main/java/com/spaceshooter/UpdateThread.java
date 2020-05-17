package com.spaceshooter;


public class UpdateThread extends Thread {

    private final GameEngine gameEngine;
    private boolean isGameRunning = true;
    private boolean isGamePaused = false;

    private Object mLock = new Object();

    public UpdateThread(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void start() {
        isGameRunning = true;
        isGamePaused = false;
        super.start();
    }

    public void stopGame() {
        isGameRunning = false;
        resumeGame();
    }

    @Override
    public void run() {
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis = System.currentTimeMillis();

        while ( isGameRunning ) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if ( isGamePaused ) {
                while ( isGamePaused ) {
                    try {
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    } catch (InterruptedException e) {
                        // We stay on the loop
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            gameEngine.onUpdate(elapsedMillis);
            previousTimeMillis = currentTimeMillis;
        }
    }

    public void pauseGame() {
        isGamePaused = true;
    }

    public void resumeGame() {
        if ( isGamePaused ) {
            isGamePaused = false;
            synchronized (mLock) {
                mLock.notify();
            }
        }
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }
}
