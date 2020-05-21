package com.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AsteroidPool extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 5000;
    private long mCurrentMillis;

    private List<Asteroid> asteroids = new ArrayList<>();

    private int mEnemiesSpawned;

    public AsteroidPool( Bitmap bitmap, GameUiParameters parameters, int poolSize ) {
        for ( int i = 0; i < poolSize; i++ ) {
            asteroids.add( new Asteroid( bitmap, parameters, this ) );
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
            Asteroid a = asteroids.remove( 0 );
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

    @Override
    public void onPostUpdate( ) {
        return; // do nothing
    }

    public void returnToPool( Asteroid asteroid ) {
        asteroids.add( asteroid );
    }

    public List<Asteroid> getAsteroids( ) {
        return Collections.unmodifiableList( asteroids );
    }
}
