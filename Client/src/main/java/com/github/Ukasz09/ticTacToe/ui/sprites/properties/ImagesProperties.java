package com.github.Ukasz09.ticTacToe.ui.sprites.properties;

import javafx.scene.image.Image;

public class ImagesProperties {
    private static final int AVATARS_QTY = 14;

    //singletons
    private static Image schemeImage;
    private static Image empty;

    //----------------------------------------------------------------------------------------------------------------//
    public static Image schemeSpriteForImageView() {
        if (schemeImage == null) {
            String imagePath = "images/decorations/testedForImageView.png";
            schemeImage = new Image(imagePath);
        }
        return schemeImage;
    }

    public static Image empty() {
        if (empty == null) {
            String imagePath = "images/decorations/empty.png";
            empty = new Image(imagePath);
        }
        return empty;
    }

    public static Image startGameBackground() {
        String imagePath = "images/backgrounds/background.png";
        return new Image(imagePath);
    }

    public static Image[] avatars() {
        String imagePathPrefix = "images/avatars/";
        Image[] images = new Image[AVATARS_QTY];
        for (int i = 0; i < AVATARS_QTY; i++)
            images[i] = new Image(imagePathPrefix + (i + 1) + ".png");
        return images;
    }

    public static Image waitingAnimation() {
        String imagePath = "images/decorations/waiting.gif";
        return new Image(imagePath);
    }

    public static Image loseAnimation() {
        String imagePath = "images/decorations/lose.gif";
        return new Image(imagePath);
    }

    public static Image winAnimation() {
        String imagePath = "images/decorations/win.gif";
        return new Image(imagePath);
    }

    public static Image drawAnimation() {
        String imagePath = "images/decorations/draw.gif";
        return new Image(imagePath);
    }

    public static Image endAnimation() {
        String imagePath = "images/decorations/end.gif";
        return new Image(imagePath);
    }

    public static Image startAnimation() {
        String imagePath = "images/decorations/start.gif";
        return new Image(imagePath);
    }
}
