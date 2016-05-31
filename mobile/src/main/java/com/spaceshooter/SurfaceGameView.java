package com.spaceshooter;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class SurfaceGameView extends SurfaceView implements SurfaceHolder.Callback, GameView {

    private boolean ready;
    private List<GameObject> gameObjects;

    public SurfaceGameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context c, AttributeSet attrs, int defStyleAttr) {
        super(c, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ready = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ready = false;
    }

    @Override
    public void draw() {
        if (!ready) {
            return;
        }

        Canvas canvas = getHolder().lockCanvas();
        if(canvas == null) {
            return;
        }

        canvas.drawRGB(0,0,0);
        synchronized (gameObjects){
            int numObjects = gameObjects.size();
            for (int i = 0; i < numObjects; i++) {
                gameObjects.get(i).onDraw(canvas);
            }
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
