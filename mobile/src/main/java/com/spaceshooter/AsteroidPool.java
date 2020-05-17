package com.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;


public class AsteroidPool extends GameObject {

    private static final int NUM_WAVES = 3;
    private static final int TIME_BETWEEN_ENEMIES = 500;
    private long mCurrentMillis;
    private List<Asteroid> mAsteroidPool = new ArrayList<>();
    private int mEnemiesSpawned;
    private int[] mWaveStartingTimestamp = new int[]{
            0000, 15000, 20000
    };

    public AsteroidPool( Bitmap bitmap, GameUiParameters parameters ) {
        for ( int i = 0; i < 10; i++ ) {
            mAsteroidPool.add( new Asteroid( bitmap, parameters, this ) );
        }
    }

    @Override
    public void startGame( ) {
        mCurrentMillis = 0;
        mEnemiesSpawned = 0;
    }

    @Override
    public void onUpdate( long elapsedMillis, GameEngine gameEngine ) {
        mCurrentMillis += elapsedMillis;
        long waveTimestamp = mEnemiesSpawned * TIME_BETWEEN_ENEMIES; // - mWaveStartingTimestamp[i];
        if ( mCurrentMillis > waveTimestamp ) {
            Asteroid a = mAsteroidPool.remove( 0 );
            a.init( gameEngine );
            gameEngine.addGameObject( a );
            mEnemiesSpawned++;
            return;
        }
    }

    @Override
    public void onDraw( Canvas canvas ) {
        // This game object does not draw anything
    }

    public void returnToPool( Asteroid asteroid ) {
        mAsteroidPool.add( asteroid );
    }
}
