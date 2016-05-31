package com.spaceshooter;

import java.util.Timer;

public class DrawThread extends Thread {

    private static int EXPECTED_FPS = 30;
    private static final long TIME_BETWEEN_DRAWS = 1000 / EXPECTED_FPS;

    private final GameEngine mGameEngine;
    private Timer mTimer;
    private boolean mGameIsRunning = true;
    private boolean mPauseGame = false;
    private java.lang.Object mLock = new Object();

    public DrawThread(GameEngine gameEngine) {
        mGameEngine = gameEngine;
    }

    public void stopGame() {
        mGameIsRunning = false;
    }

    public void pauseGame() {
        mPauseGame = true;
    }

    public void resumeGame() {
        mPauseGame = false;
    }

    @Override
    public void run() {
        long elapsedMillis;
        long currentTimeMillis;
        long previousTimeMillis = System.currentTimeMillis();

        while (mGameIsRunning) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if (mPauseGame) {
                while (mPauseGame) {
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
            if (elapsedMillis < 20) { // This is 50 fps
                try {
                    Thread.sleep(20 - elapsedMillis);
                } catch (InterruptedException e) {
                    // We just continue.
                }
            }
            mGameEngine.onDraw();
            previousTimeMillis = currentTimeMillis;
        }
    }
}
