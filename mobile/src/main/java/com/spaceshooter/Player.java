package com.spaceshooter;

import android.view.View;
import android.widget.TextView;

public class Player extends GameObject {
    private double positionX;
    private double positionY;

    private double speedFactor;
    private int maxX;
    private int maxY;

    private final TextView mTextView;

    double mSpeedFactor;

    public Player(View view) {
        mTextView = (TextView) view.findViewById(R.id.txt_score);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedTime, GameEngine gameEngine) {
        InputController inputController = gameEngine.inputController;
        positionX += speedFactor * inputController.mHorizontalFactor * elapsedTime;

        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += mSpeedFactor*inputController.mVerticalFactor*elapsedTime ;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    @Override
    public void onDraw() {
        mTextView.setText("["+(int) (positionX)+","+(int) (positionY)+"]");
    }

    @Override
    protected void onRemovedFromGameUiThread() {

    }

    @Override
    protected void onAddedToGameUiThread() {

    }
}
