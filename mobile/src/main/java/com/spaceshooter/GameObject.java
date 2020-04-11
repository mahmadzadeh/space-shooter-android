package com.spaceshooter;

import android.graphics.Canvas;

public abstract class GameObject {

    public final Runnable onAddedRunnable = ( ) -> onAddedToGameUiThread();
    public final Runnable onRemovedRunnable = ( ) -> onRemovedFromGameUiThread();

    public abstract void startGame();

    public abstract void onUpdate(long elapsedTime, GameEngine gameEngine);

    public void onRemovedFromGameUiThread() {

    }

    public void onAddedToGameUiThread() {

    }

    public abstract void onDraw(Canvas canvas);

    public void onPostUpdate(GameEngine gameEngine) {
    }
}
