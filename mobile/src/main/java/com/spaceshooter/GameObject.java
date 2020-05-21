package com.spaceshooter;

import android.graphics.Canvas;

public abstract class GameObject {

    public abstract void startGame( );

    public abstract void onUpdate( long elapsedTime, GameEngine gameEngine );

    public abstract void onDraw( Canvas canvas );

    public abstract void onPostUpdate( ) ;
}
