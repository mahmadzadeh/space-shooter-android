package com.spaceshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameBitmapFactory {

    private static GameBitmapFactory instance;
    private Resources resources;

    private GameBitmapFactory( Resources resources ) {
        this.resources = resources;
    }

    public static GameBitmapFactory getInstance( Resources resources ) {
        if ( instance == null ) {
            instance = new GameBitmapFactory( resources );
            return instance;
        } else {
            return instance;
        }
    }

    public Bitmap getBitmap( int resourceId ) {
        return BitmapFactory.decodeResource( resources, resourceId );
    }


}
