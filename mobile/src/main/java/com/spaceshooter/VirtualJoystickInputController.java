package com.spaceshooter;


import android.view.MotionEvent;
import android.view.View;

public class VirtualJoystickInputController extends InputController {

    private final double maxDistance;
    private float startingPositionX;
    private float startingPositionY;

    public VirtualJoystickInputController( View view ) {
        view.findViewById( R.id.vjoystick_main ).setOnTouchListener( new VJoystickTouchListener() );
        view.findViewById( R.id.vjoystick_touch ).setOnTouchListener( new VFireButtonTouchListener() );

        double pixelFactor = view.getHeight() / 400d;
        maxDistance = 50 * pixelFactor;
    }


    private class VJoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch( View v, MotionEvent event ) {
            int action = event.getActionMasked();
            if ( action == MotionEvent.ACTION_DOWN ) {
                startingPositionX = event.getX( 0 );
                startingPositionY = event.getY( 0 );
            } else if ( action == MotionEvent.ACTION_UP ) {
                setHorizontalFactor( 0 );
                setVerticalFactor( 0 );
            } else if ( action == MotionEvent.ACTION_MOVE ) {
                // Get the proportion to the max
                setHorizontalFactor( ( event.getX( 0 ) - startingPositionX ) / maxDistance );
                if ( getHorizontalFactor() > 1 ) {
                    setHorizontalFactor( 1 );
                } else if ( getHorizontalFactor() < -1 ) {
                    setHorizontalFactor( -1 );
                }
                setVerticalFactor( ( event.getY( 0 ) - startingPositionY ) / maxDistance );
                if ( getVerticalFactor() > 1 ) {
                    setVerticalFactor( 1 );
                } else if ( getVerticalFactor() < -1 ) {
                    setVerticalFactor( -1 );
                }
            }
            return true;
        }
    }

    private class VFireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch( View v, MotionEvent event ) {
            int action = event.getActionMasked();
            if ( action == MotionEvent.ACTION_DOWN ) {
                setFiring( true );
            } else if ( action == MotionEvent.ACTION_UP ) {
                setFiring( false );
            }
            return true;
        }
    }
}
