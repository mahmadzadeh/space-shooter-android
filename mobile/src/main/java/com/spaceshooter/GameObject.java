package com.spaceshooter;

import android.graphics.Canvas;

public abstract class GameObject {

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };
    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public abstract void startGame();

    public abstract void onUpdate(long elapsedTime, GameEngine gameEngine);

    public void onRemovedFromGameUiThread() {

    }

    public void onAddedToGameUiThread(){

    }

    public abstract void onDraw(Canvas canvas);
}
