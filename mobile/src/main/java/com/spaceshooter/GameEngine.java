package com.spaceshooter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;


public class GameEngine {

    private final GameView mGameView;
    public final double mPixelFactor;
    private List<GameObject> mGameObjects = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToRemove = new ArrayList<GameObject>();

    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    public InputController inputController;
    public final int mWidth;
    public final int mHeight;
    private Activity mActivity;
    public Random mRandom = new Random();

    public GameEngine (Activity a, GameView gameView)  {
        mActivity = a;
        mGameView = gameView;
        gameView.setGameObjects(mGameObjects);
        mWidth = gameView.getWidth()
                - gameView.getPaddingRight() - gameView.getPaddingRight();
        mHeight = gameView.getHeight()
                - gameView.getPaddingTop() - gameView.getPaddingBottom();

        mPixelFactor = mHeight / 400d;

    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = mGameObjects.size();
        for (int i=0; i<numGameObjects; i++) {
            mGameObjects.get(i).startGame();
        }

        // Start the update thread
        mUpdateThread = new UpdateThread(this);
        mUpdateThread.start();

        // Start the drawing thread
        mDrawThread = new DrawThread(this);
        mDrawThread.start();
    }

    public void stopGame() {
        if (mUpdateThread != null) {
            mUpdateThread.stopGame();
        }
        if (mDrawThread != null) {
            mDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (mUpdateThread != null) {
            mUpdateThread.pauseGame();
        }
        if (mDrawThread != null) {
            mDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (mUpdateThread != null) {
            mUpdateThread.resumeGame();
        }
        if (mDrawThread != null) {
            mDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()){
            mObjectsToAdd.add(gameObject);
        }
        else {
            mGameObjects.add(gameObject);
        }
        mActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        mObjectsToRemove.add(gameObject);
        mActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        //inputController.onPreUpdate();
        int numGameObjects = mGameObjects.size();
        for (int i=0; i<numGameObjects; i++) {
            mGameObjects.get(i).onUpdate(elapsedMillis, this);
        }
        synchronized (mGameObjects) {
            while (!mObjectsToRemove.isEmpty()) {
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while (!mObjectsToAdd.isEmpty()) {
                mGameObjects.add(mObjectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        mGameView.draw();
    }

    public boolean isRunning() {
        return mUpdateThread != null && mUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return mUpdateThread != null && mUpdateThread.isGamePaused();
    }

    public void setInputController(InputController controller) {
        inputController = controller;
    }

    public Context getContext() {
        return mGameView.getContext();
    }
}
