package com.spaceshooter;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {

    public final double pixelFactor;
    public final int width;
    public final int height;
    private final GameView gameView;
    public InputController inputController;
    public Random random = new Random();
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> gameObjectsToAdd = new ArrayList<>();
    private List<GameObject> gameObjectsToRemove = new ArrayList<>();

    private List<ScreenGameObject> collidableObjectsToAdd = new ArrayList<>();
    private List<ScreenGameObject> collidableObjects = new ArrayList<>();

    private UpdateThread updateThread;
    private DrawThread drawThread;

    public GameEngine( GameView gameView ) {
        this.gameView = gameView;
        this.gameView.setGameObjects( gameObjects );
        width = gameView.getWidth()
                - gameView.getPaddingRight() - gameView.getPaddingLeft();
        height = gameView.getHeight()
                - gameView.getPaddingTop() - gameView.getPaddingBottom();

        pixelFactor = height / 400d;
    }

    public void startGame( ) {
        stopGame();

        int numGameObjects = gameObjects.size();
        for ( int i = 0; i < numGameObjects; i++ ) {
            gameObjects.get( i ).startGame();
        }

        updateThread = new UpdateThread( this );
        updateThread.start();

        drawThread = new DrawThread( this );
        drawThread.start();
    }

    public void stopGame( ) {
        if ( updateThread != null ) {
            updateThread.stopGame();
        }
        if ( drawThread != null ) {
            drawThread.stopGame();
        }
    }

    public void pauseGame( ) {
        if ( updateThread != null ) {
            updateThread.pauseGame();
        }
        if ( drawThread != null ) {
            drawThread.pauseGame();
        }
    }

    public void resumeGame( ) {
        if ( updateThread != null ) {
            updateThread.resumeGame();
        }
        if ( drawThread != null ) {
            drawThread.resumeGame();
        }
    }

    public void addGameObject( GameObject gameObject ) {
        if ( isRunning() ) {
            gameObjectsToAdd.add( gameObject );
        } else {
            gameObjects.add( gameObject );
        }

        addToCollidableObjects( gameObject );
    }

    private void addToCollidableObjects( GameObject gameObject ) {
        if ( ( gameObject instanceof Player ) ||
                ( gameObject instanceof Bullet ) ||
                ( gameObject instanceof Asteroid ) ) {

            collidableObjectsToAdd.add( ( ScreenGameObject ) gameObject );
        }
    }

    public void removeGameObject( ScreenGameObject gameObject ) {
        gameObjectsToRemove.add( gameObject );
    }

    public void onUpdate( long elapsedMillis ) {

        int numGameObjects = gameObjects.size();

        for ( int i = 0; i < numGameObjects; i++ ) {
            gameObjects.get( i ).onUpdate( elapsedMillis, this );
            gameObjects.get( i ).onPostUpdate();
        }

        checkCollisions();

        synchronized ( gameObjects ) {
            while ( !gameObjectsToRemove.isEmpty() ) {
                GameObject gameObjectToRemove = gameObjectsToRemove.remove( 0 );
                gameObjects.remove( gameObjectToRemove );
                collidableObjects.remove( gameObjectToRemove );
            }
            while ( !gameObjectsToAdd.isEmpty() ) {
                gameObjects.add( gameObjectsToAdd.remove( 0 ) );
                collidableObjects.add( collidableObjectsToAdd.remove( 0 ) );
            }
        }
    }

    public void onDraw( ) {
        gameView.draw();
    }

    public boolean isRunning( ) {
        return updateThread != null && updateThread.isGameRunning();
    }

    public boolean isPaused( ) {
        return updateThread != null && updateThread.isGamePaused();
    }

    public void setInputController( InputController controller ) {
        inputController = controller;
    }

    public Context getContext( ) {
        return gameView.getContext();
    }

    public void checkCollisions( ) {

        int numObjects = collidableObjects.size();
        for ( int i = 0; i < numObjects; i++ ) {
            ScreenGameObject screenGameObjectA = collidableObjects.get( i );
            for ( int j = i + 1; j < numObjects; j++ ) {
                ScreenGameObject screenGameObjectB = collidableObjects.get( j );
                if ( screenGameObjectA instanceof Asteroid && screenGameObjectB instanceof Asteroid ) {
                    continue;
                }
                if ( ( screenGameObjectA instanceof Bullet && screenGameObjectB instanceof Player ) ||
                        ( screenGameObjectA instanceof Player && screenGameObjectB instanceof Bullet ) ) {
                    continue;
                }
                if ( screenGameObjectA.checkCollision( screenGameObjectB ) ) {
                    Log.e( "collided objects", "checkCollisions: screenGameObjectA"+screenGameObjectA.toString() + " screenGameObjectB " + screenGameObjectB.toString() );
                    screenGameObjectA.onCollision( this, screenGameObjectB );
                }
            }
        }
    }
}
