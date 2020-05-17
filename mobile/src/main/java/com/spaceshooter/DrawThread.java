package com.spaceshooter;

import java.util.Timer;

public class DrawThread extends Thread {

    private static int EXPECTED_FPS = 30;
    private static final long TIME_BETWEEN_DRAWS = 1000 / EXPECTED_FPS;

    private final GameEngine gameEngine;
    private Timer mTimer;
    private boolean isGameRunning = true;
    private boolean isGamePaused = false;
    private java.lang.Object lock = new Object();

    public DrawThread(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void stopGame() {
        isGameRunning = false;
    }

    public void pauseGame() {
        isGamePaused = true;
    }

    public void resumeGame() {
        isGamePaused = false;
    }

    @Override
    public void run() {
        long elapsedMillis;
        long currentTimeMillis;
        long previousTimeMillis = System.currentTimeMillis();

        while ( isGameRunning ) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if ( isGamePaused ) {
                while ( isGamePaused ) {
                    try {
                        synchronized ( lock ) {
                            lock.wait();
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
            gameEngine.onDraw();
            previousTimeMillis = currentTimeMillis;
        }
    }
}
