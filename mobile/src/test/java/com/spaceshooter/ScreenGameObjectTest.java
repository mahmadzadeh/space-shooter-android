package com.spaceshooter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScreenGameObjectTest {


    @Mock
    private GameEngine mockGameEngine;

    @Mock
    private Context mockContext;

    @Mock
    private Resources mockResource;

    @Mock
    private Drawable mockDrawable;

    @Mock
    private Bitmap mockBitMap;

    @Mock
    private GameUiParameters mockGameParams;

    @Test
    public void checkCollision_whenTwoGameObjectsNotCollided_thenReturnFalse( ) {

        ScreenGameObject bullet = new Bullet( mockBitMap, mockGameParams);
        ScreenGameObject player = new Player( mockBitMap, Collections.EMPTY_LIST, mockGameParams);

        //player.
    }


    @Test
    public void onCollision( ) {
    }

    @Test
    public void onPostUpdate( ) {
    }
}