package com.spaceshooter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

public class GameFragment extends SpaceShooterBaseFragment implements View.OnClickListener {

    private GameEngine mGameEngine;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);

        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            observer.removeGlobalOnLayoutListener(this);
                        }
                        else {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                        GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                        mGameEngine = new GameEngine(getActivity(), gameView);
                        mGameEngine.setInputController(new VirtualJoystickInputController(view));
                        mGameEngine.addGameObject(new Player(mGameEngine));
                        mGameEngine.addGameObject(new FPSCounter(mGameEngine));
                        mGameEngine.startGame();
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (mGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        mGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mGameEngine.stopGame();
                        ((SpaceShooterActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mGameEngine.resumeGame();
                    }
                })
                .create()
                .show();

    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (mGameEngine.isPaused()) {
            mGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            mGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }

//    private void startOrStop() {
//        Button button = (Button) getView().findViewById(R.id.btn_start_stop);
//        Button playPauseButton = (Button) getView().findViewById(R.id.btn_play_pause);
//        if (mGameEngine.isRunning()) {
//            mGameEngine.stopGame();
//            button.setText(R.string.start);
//            playPauseButton.setEnabled(false);
//        }
//        else {
//            mGameEngine.startGame();
//            button.setText(R.string.stop);
//            playPauseButton.setEnabled(true);
//            playPauseButton.setText(R.string.pause);
//        }
//    }
}