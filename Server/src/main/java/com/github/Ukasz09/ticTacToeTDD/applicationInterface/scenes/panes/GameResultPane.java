package com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.panes;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.gameSprites.OscarStatue;

public class GameResultPane extends CenteredPane implements IDrawingGraphic {
    private static final double SPRITE_WIDTH_PROPORTION = 7 / 8d;
    private static final double SPRITE_HEIGHT_PROPORTION = 7 / 8d;

    private IDrawingGraphic spriteToRender = null;

    //----------------------------------------------------------------------------------------------------------------//
    public GameResultPane(double width, double positionX, double positionY) {
        super();
        setUpPane(width, positionX, positionY);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setUpPane(double width, double positionX, double positionY) {
        setPageWidth(width);
        setLayoutX(positionX);
        setLayoutY(positionY);
    }

    private void setPageWidth(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    public void addOscarStatue() {
        double statueWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        OscarStatue oscarStatueSprite = new OscarStatue(statueWidth, getSpriteHeight(), getSpriteCenterPositionX(statueWidth), getLayoutY());
        spriteToRender = oscarStatueSprite;
    }

    private double getSpriteHeight() {
        return (manager.getBottomFrameBorder() - getLayoutY()) * SPRITE_HEIGHT_PROPORTION;
    }

    private double getSpriteCenterPositionX(double statueWidth) {
        return (getLayoutX() + getWidth() / 2 - statueWidth / 2);
    }

    @Override
    public void render() {
        if (spriteToRender != null)
            spriteToRender.render();
    }

    @Override
    public void update() {
        if (spriteToRender != null)
            spriteToRender.update();
    }
}
