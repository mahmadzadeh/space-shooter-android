package com.spaceshooter;

import android.view.MotionEvent;
import android.view.View;

public class BasicInputController extends InputController implements View.OnTouchListener {

    public BasicInputController(View view) {
        view.findViewById(R.id.keypad_up).setOnTouchListener(this);
        view.findViewById(R.id.keypad_down).setOnTouchListener(this);
        view.findViewById(R.id.keypad_left).setOnTouchListener(this);
        view.findViewById(R.id.keypad_right).setOnTouchListener(this);
        view.findViewById(R.id.keypad_fire).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        int id = v.getId();
        if (action == MotionEvent.ACTION_DOWN) {
            // User started pressing a key
            if (id == R.id.keypad_up) {
                setVerticalFactor( getVerticalFactor() - 1);
            } else if (id == R.id.keypad_down) {
                setVerticalFactor( getVerticalFactor() + 1);
            } else if (id == R.id.keypad_left) {
                setHorizontalFactor(getHorizontalFactor() - 1);
            } else if (id == R.id.keypad_right) {
                setHorizontalFactor(getHorizontalFactor() + 1);
            } else if (id == R.id.keypad_fire) {
                setFiring( true );
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (id == R.id.keypad_up) {
                setVerticalFactor( getVerticalFactor() + 1);
            } else if (id == R.id.keypad_down) {
                setVerticalFactor( getVerticalFactor() - 1);
            } else if (id == R.id.keypad_left) {
                setHorizontalFactor(getHorizontalFactor() + 1);
            } else if (id == R.id.keypad_right) {
                setHorizontalFactor(getHorizontalFactor() - 1);
            } else if (id == R.id.keypad_fire) {
                setFiring( false);
            }
        }
        return false;
    }

}
