package com.spaceshooter;


import android.view.MotionEvent;
import android.view.View;

public class VirtualJoystickInputController extends InputController {

    private float startingPositionX;
    private float startingPositionY;

    private final double maxDistance;

    public VirtualJoystickInputController(View view) {
        view.findViewById(R.id.vjoystick_main).setOnTouchListener(new VJoystickTouchListener());
        view.findViewById(R.id.vjoystick_touch).setOnTouchListener(new VFireButtonTouchListener());

        double pixelFactor = view.getHeight() / 400d;
        maxDistance = 50 * pixelFactor;
    }


    private class VJoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                startingPositionX = event.getX(0);
                startingPositionY = event.getY(0);
            } else if (action == MotionEvent.ACTION_UP) {
                horizontalFactor = 0;
                verticalFactor = 0;
            } else if (action == MotionEvent.ACTION_MOVE) {
                // Get the proportion to the max
                horizontalFactor = (event.getX(0) - startingPositionX ) / maxDistance;
                if ( horizontalFactor > 1) {
                    horizontalFactor = 1;
                } else if ( horizontalFactor < -1) {
                    horizontalFactor = -1;
                }
                verticalFactor = (event.getY(0) - startingPositionY ) / maxDistance;
                if ( verticalFactor > 1) {
                    verticalFactor = 1;
                } else if ( verticalFactor < -1) {
                    verticalFactor = -1;
                }
            }
            return true;
        }
    }

    private class VFireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                isFiring = true;
            } else if (action == MotionEvent.ACTION_UP) {
                isFiring = false;
            }
            return true;
        }
    }
}
