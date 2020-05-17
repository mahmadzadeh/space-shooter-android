package com.spaceshooter;

public class GameUiParameters {
    private final GameEngineDimension gameEngineDimension;
    private final int drawableWidth;
    private final int drawableHeight;

    private final double pixelFactor;

    public GameUiParameters( GameEngineDimension gameEngineDimension, double pixelFactor, int drawableWidth, int drawableHeight ) {
        this.gameEngineDimension= gameEngineDimension;
        this.drawableWidth = drawableWidth;
        this.drawableHeight = drawableHeight;
        this.pixelFactor = pixelFactor;
    }

    public GameEngineDimension getGameEngineDimension( ) {
        return gameEngineDimension;
    }

    public int getDrawableWidth( ) {
        return drawableWidth;
    }

    public int getDrawableHeight( ) {
        return drawableHeight;
    }

    public double getPixelFactor( ) {
        return pixelFactor;
    }
}
