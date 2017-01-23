package com.spaceshooter;


import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {

    public final double mPixelFactor;
    public final int mWidth;
    public final int mHeight;
    private final GameView mGameView;
    public InputController inputController;
    public Random mRandom = new Random();

    private List<GameObject> mGameObjects = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToRemove = new ArrayList<GameObject>();

    private List<ScreenGameObject> mCollidableObjectsToAdd = new ArrayList<ScreenGameObject>();
    private List<ScreenGameObject> mCollisionableObjects = new ArrayList<>();

    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    private Activity mActivity;

    public GameEngine(Activity a, GameView gameView) {
        mActivity = a;
        mGameView = gameView;
        mGameView.setGameObjects(mGameObjects);
        mWidth = gameView.getWidth()
                - gameView.getPaddingRight() - gameView.getPaddingRight();
        mHeight = gameView.getHeight()
                - gameView.getPaddingTop() - gameView.getPaddingBottom();

        mPixelFactor = mHeight / 400d;

    }

    public void startGame() {
        stopGame();

        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).startGame();
        }

        mUpdateThread = new UpdateThread(this);
        mUpdateThread.start();

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
        if (isRunning()) {
            mObjectsToAdd.add(gameObject);
            //addToCollidableObjects(gameObject);
        } else {
            mGameObjects.add(gameObject);
        }

        addToCollidableObjects(gameObject);

        mActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    private void addToCollidableObjects(GameObject gameObject) {
        if ((gameObject instanceof Player) ||
                (gameObject instanceof Bullet) ||
                (gameObject instanceof Asteroid)) {

            mCollidableObjectsToAdd.add((ScreenGameObject) gameObject);
        }

    }

    public void removeGameObject(ScreenGameObject gameObject) {
        mObjectsToRemove.add(gameObject);
        mActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {

        int numGameObjects = mGameObjects.size();

        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).onUpdate(elapsedMillis, this);
            mGameObjects.get(i).onPostUpdate(this);
        }

        checkCollisions();

        synchronized (mGameObjects) {
            while (!mObjectsToRemove.isEmpty()) {
                GameObject gameObjectToRemove = mObjectsToRemove.remove(0);
                mGameObjects.remove(gameObjectToRemove);
                mCollisionableObjects.remove(gameObjectToRemove);
            }
            while (!mObjectsToAdd.isEmpty()) {
                mGameObjects.add(mObjectsToAdd.remove(0));
                mCollisionableObjects.add(mCollidableObjectsToAdd.remove(0));
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

    public void checkCollisions() {

        int numObjects = mCollisionableObjects.size();
        for (int i = 0; i < numObjects; i++) {
            ScreenGameObject screenGameObjectA = mCollisionableObjects.get(i);
            for (int j = i + 1; j < numObjects; j++) {
                ScreenGameObject screenGameObjectB = mCollisionableObjects.get(j);
                if (screenGameObjectA instanceof Asteroid && screenGameObjectB instanceof Asteroid) {
                    continue;
                }
                if ((screenGameObjectA instanceof Bullet && screenGameObjectB instanceof Player) ||
                        (screenGameObjectA instanceof Player && screenGameObjectB instanceof Bullet)) {
                    continue;
                }
                if (screenGameObjectA.checkCollision(screenGameObjectB)) {
                    screenGameObjectA.onCollision(this, screenGameObjectB);
                }
            }
        }
    }
}
