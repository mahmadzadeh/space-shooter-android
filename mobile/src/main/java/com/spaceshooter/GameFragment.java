package com.spaceshooter;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends SpaceShooterBaseFragment implements View.OnClickListener {

    private GameEngine gameEngine;

    public GameFragment( ) {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_game, container, false );
    }

    @Override
    public void onViewCreated( final View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        view.findViewById( R.id.btn_play_pause ).setOnClickListener( this );

        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout( ) {
                        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ) {
                            observer.removeGlobalOnLayoutListener( this );
                        } else {
                            observer.removeOnGlobalLayoutListener( this );
                        }
                        GameView gameView = getView().findViewById( R.id.gameView );
                        gameEngine = new GameEngine( gameView );
                        gameEngine.setInputController( new VirtualJoystickInputController( view ) );

                        gameEngine.addGameObject( getBackGroundGameObject() );

                        gameEngine.addGameObject( getAsteroidPoolGameObject() );
                        gameEngine.addGameObject( getPlayerGameObject() );
                        gameEngine.addGameObject( new FPSCounter( gameEngine ) );
                        gameEngine.startGame();
                    }
                }
        );
    }

    @Override
    public void onClick( View v ) {
        if ( v.getId() == R.id.btn_play_pause ) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause( ) {
        super.onPause();
        if ( gameEngine.isRunning() ) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy( ) {
        super.onDestroy();
        gameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed( ) {
        if ( gameEngine.isRunning() ) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog( ) {
        gameEngine.pauseGame();
        new AlertDialog.Builder( getActivity() )
                .setTitle( R.string.pause_dialog_title )
                .setMessage( R.string.pause_dialog_message )
                .setPositiveButton( R.string.resume, ( dialog, which ) -> {
                    dialog.dismiss();
                    gameEngine.resumeGame();
                } )
                .setNegativeButton( R.string.stop, ( dialog, which ) -> {
                    dialog.dismiss();
                    gameEngine.stopGame();
                    ( ( SpaceShooterActivity ) getActivity() ).navigateBack();
                } )
                .setOnCancelListener( dialog -> gameEngine.resumeGame() )
                .create()
                .show();

    }

    private void playOrPause( ) {
        Button button = ( Button ) getView().findViewById( R.id.btn_play_pause );
        if ( gameEngine.isPaused() ) {
            gameEngine.resumeGame();
            button.setText( R.string.pause );
        } else {
            gameEngine.pauseGame();
            button.setText( R.string.resume );
        }
    }

    private ParallaxBackground getBackGroundGameObject( ) {
        Resources resources = gameEngine.getContext().getResources();

        Drawable drawable = resources.getDrawable( R.drawable.hubble_udf );

        Bitmap backgroundBitmap = ( ( BitmapDrawable ) drawable ).getBitmap();

        GameEngineDimension gameEngineDimension = new GameEngineDimension( gameEngine.width, gameEngine.height );

        GameUiParameters parameters = new GameUiParameters( gameEngineDimension, gameEngine.pixelFactor,
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );

        return new ParallaxBackground( backgroundBitmap, 20, parameters );
    }

    private AsteroidPool getAsteroidPoolGameObject( ) {
        Resources resources = gameEngine.getContext().getResources();

        Drawable drawable = resources.getDrawable( R.drawable.a10000 );

        Bitmap backgroundBitmap = ( ( BitmapDrawable ) drawable ).getBitmap();

        GameEngineDimension gameEngineDimension = new GameEngineDimension( gameEngine.width, gameEngine.height );

        GameUiParameters parameters = new GameUiParameters( gameEngineDimension, gameEngine.pixelFactor,
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );

        return new AsteroidPool( backgroundBitmap, parameters );
    }

    private Player getPlayerGameObject( ) {
        Resources resources = gameEngine.getContext().getResources();

        Drawable drawable = resources.getDrawable( R.drawable.ship );

        Bitmap backgroundBitmap = ( ( BitmapDrawable ) drawable ).getBitmap();

        GameEngineDimension gameEngineDimension = new GameEngineDimension( gameEngine.width, gameEngine.height );

        GameUiParameters parameters = new GameUiParameters( gameEngineDimension, gameEngine.pixelFactor,
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );

        return new Player( backgroundBitmap, initBulletPool(  6) , parameters);
    }

    private List<Bullet> initBulletPool(int bulletCount) {
        Resources resources = gameEngine.getContext().getResources();

        Drawable drawable = resources.getDrawable( R.drawable.bullet );

        Bitmap backgroundBitmap = ( ( BitmapDrawable ) drawable ).getBitmap();

        GameEngineDimension gameEngineDimension = new GameEngineDimension( gameEngine.width, gameEngine.height );

        GameUiParameters parameters = new GameUiParameters( gameEngineDimension, gameEngine.pixelFactor,
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );

        List<Bullet> bullets = new ArrayList<>(  );
        for (int i = 0; i < bulletCount; i++) {
            bullets.add(new Bullet(backgroundBitmap, parameters));
        }
        return bullets;
    }

}