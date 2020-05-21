package com.spaceshooter;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AsteroidPoolTest {

    @Mock
    private Bitmap mockBitMap;

    @Mock
    private GameUiParameters mockParams;

    @Test
    public void constructor_willCreatePoolOfAsteroids( ) {
        AsteroidPool pool = new AsteroidPool( mockBitMap, mockParams, GameFragment.POOL_SIZE );
        assertEquals( GameFragment.POOL_SIZE, pool.getAsteroids().size() );
    }

    @Test
    public void asteroidsInPoolHaveLocations( ) {
        AsteroidPool pool = new AsteroidPool( mockBitMap, mockParams, GameFragment.POOL_SIZE );

        pool.getAsteroids().forEach( asteroid -> {
            assertEquals( 0, asteroid.getBoundingRect().top);
        });
    }


    @Test
    public void startGame( ) {
    }

    @Test
    public void onUpdate( ) {
    }

    @Test
    public void onDraw( ) {
    }

    @Test
    public void onPostUpdate( ) {
    }

    @Test
    public void returnToPool( ) {
    }
}