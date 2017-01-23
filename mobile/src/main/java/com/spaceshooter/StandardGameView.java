package com.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class StandardGameView extends View implements GameView {

    private List<GameObject> mGameObjects;

    public StandardGameView(Context context) {
        super(context);
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void draw() {
        postInvalidate();
    }

    @Override
    public void setGameObjects(List<GameObject> gameObjects) {
        mGameObjects = gameObjects;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (mGameObjects) {
            int numObjects = mGameObjects != null ? mGameObjects.size() : 0;
            for (int i = 0; i < numObjects; i++) {
                mGameObjects.get(i).onDraw(canvas);
            }
        }
    }
}
