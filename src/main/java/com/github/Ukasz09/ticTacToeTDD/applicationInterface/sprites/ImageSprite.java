package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import javafx.scene.image.Image;

public abstract class ImageSprite extends SpriteWithEventHandler {
    private Image spriteImage;

    public ImageSprite(double width, double height, Image spriteImage, double positionX, double positionY, boolean withImageViewInRoot) {
        super(width, height, positionX, positionY, withImageViewInRoot);
        this.spriteImage = spriteImage;
    }

    @Override
    public void render() {
        renderSprite();
    }

    private void renderSprite() {
        manager.getGraphicContext().drawImage(spriteImage, positionX, positionY, width, height);
    }

    protected void setSpriteImage(Image spriteImage) {
        this.spriteImage = spriteImage;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }
}
