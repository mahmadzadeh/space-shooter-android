package com.spaceshooter;

class GameEngineDimension {
    private final int gameEngineWidth;
    private final int gameEngineHeight;

    public GameEngineDimension( int gameEngineWidth, int gameEngineHeight ) {
        this.gameEngineWidth = gameEngineWidth;
        this.gameEngineHeight = gameEngineHeight;
    }

    public int getGameEngineWidth( ) {
        return gameEngineWidth;
    }

    public int getGameEngineHeight( ) {
        return gameEngineHeight;
    }
}
