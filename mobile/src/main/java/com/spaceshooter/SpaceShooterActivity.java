package com.spaceshooter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class SpaceShooterActivity extends Activity {

    private static final String TAG_FRAGMENT = "content";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_spaceshooter );
        if ( savedInstanceState == null ) {
            getFragmentManager().beginTransaction()
                    .add( R.id.container, new MainMenuFragment(), TAG_FRAGMENT )
                    .commit();
        }
    }

    public void startGame( ) {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new GameFragment() );
    }

    private void navigateToFragment( SpaceShooterBaseFragment dst ) {
        getFragmentManager()
                .beginTransaction()
                .replace( R.id.container, dst, TAG_FRAGMENT )
                .addToBackStack( null )
                .commit();
    }

    @Override
    public void onBackPressed( ) {
        final SpaceShooterBaseFragment fragment = ( SpaceShooterBaseFragment ) getFragmentManager().findFragmentByTag( TAG_FRAGMENT );
        if ( fragment == null || !fragment.onBackPressed() ) {
            super.onBackPressed();
        }
    }

    public void navigateBack( ) {
        // Do a push on the navigation history
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged( boolean hasFocus ) {
        super.onWindowFocusChanged( hasFocus );
        if ( hasFocus ) {
            View decorView = getWindow().getDecorView();
            if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ) {
                decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE );
            } else {
                decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
            }
        }
    }

}