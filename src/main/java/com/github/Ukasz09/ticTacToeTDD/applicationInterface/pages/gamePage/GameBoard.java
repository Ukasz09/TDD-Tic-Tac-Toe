package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.GameBoxButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.SignButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.*;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class GameBoard implements IDrawingGraphic {
    private static final int DEFAULT_BOARD_SIZE = 3;
    private static final double BOARD_SIZE_PROPORTION = 4 / 5d;
    private static final double SIGN_TO_BOARD_PROPORTION = 0.5;

    private ViewManager manager;
    private GameBoxButtonSprite[] boxButtonSprites;
    private List<SignButtonSprite> signButtonSprites;
    private Point2D lastChosenBoxCoords;
    private int boardSize = DEFAULT_BOARD_SIZE;
    private double labelPaneHeight;

    //-----------------------------------------------------------------------------------------------------------------//
    public GameBoard(double labelPaneHeight) {
        initializeGameBoard(labelPaneHeight);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeGameBoard(double labelPaneHeight) {
        manager = ViewManager.getInstance();
        signButtonSprites = new ArrayList<>();
        this.labelPaneHeight = labelPaneHeight;
    }

    public void initializeGameGrid(int boardSize, IEventKindObserver observer) throws IncorrectBoardSizeException {
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();
        this.boardSize = boardSize;
        boxButtonSprites = new GameBoxButtonSprite[boardSize * boardSize];
        addGameGridBoxes(boardSize, observer);
    }

    private void addGameGridBoxes(int boardSize, IEventKindObserver observer) {
        double buttonSize = getGridButtonSize();
        double startedPositionX = getFirstButtonXPositionToCenterWithOthers(boardSize, 0, buttonSize);
        int offset = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                boxButtonSprites[offset] = getNewBox(row, column, startedPositionX, getFirstButtonYPosition(), buttonSize, observer);
                offset++;
            }
        }
    }

    private double getGridButtonSize() {
        return (manager.getBottomFrameBorder() - labelPaneHeight) * BOARD_SIZE_PROPORTION / boardSize;
    }


    private double getFirstButtonXPositionToCenterWithOthers(int buttonsInRowQty, double buttonsPadding, double buttonWidth) {
        return ((manager.getRightFrameBorder() - buttonsInRowQty * buttonWidth - (buttonsInRowQty - 1) * buttonsPadding) / 2);
    }

    private double getFirstButtonYPosition() {
        return labelPaneHeight;
    }

    private GameBoxButtonSprite getNewBox(int rowIndex, int columnIndex, double startedPositionX, double startedPositionY, double buttonSize, IEventKindObserver observer) {
        GameBoxButtonSprite box = new GameBoxButtonSprite(rowIndex, columnIndex, buttonSize);
        setBoxPosition(box, startedPositionX, startedPositionY, rowIndex, columnIndex);
        box.attachObserver(observer);
        addGameBoxOnMouseClickedEvent(box);
        return box;
    }

    private void setBoxPosition(GameBoxButtonSprite box, double startedPositionX, double startedPositionY, int rowIndex, int columnIndex) {
        box.setPositionX(getBoxPositionX(rowIndex, box.getWidth(), startedPositionX));
        box.setPositionY(getBoxPositionY(columnIndex, box.getHeight(), startedPositionY));
    }

    private double getBoxPositionX(int rowIndex, double buttonWidth, double startedPosition) {
        return startedPosition + rowIndex * buttonWidth;
    }

    private double getBoxPositionY(int columnIndex, double buttonHeight, double startedPosition) {
        return startedPosition + columnIndex * buttonHeight;
    }

    public void addSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        SignButtonSprite sign =
                new SignButtonSprite(signSheetProperty, getGridButtonSize() * SIGN_TO_BOARD_PROPORTION, false);
        setSignPosition(sign, rowIndex, columnIndex);
        signButtonSprites.add(sign);
    }

    private void setSignPosition(SignButtonSprite sign, int rowIndex, int columnIndex) {
        double buttonSize = boxButtonSprites[0].getWidth();
        double buttonStartedPositionX = getFirstButtonXPositionToCenterWithOthers(boardSize, 0, buttonSize);
        sign.setPositionX(getBoxPositionX(rowIndex, buttonSize, buttonStartedPositionX) + buttonSize / 2 - sign.getWidth() / 2);
        sign.setPositionY(getBoxPositionY(columnIndex, buttonSize, getFirstButtonYPosition() + buttonSize / 2 - sign.getHeight() / 2));
    }

    private void addGameBoxOnMouseClickedEvent(GameBoxButtonSprite box) {
        box.addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (box.isActive()) {
                box.disable();
                lastChosenBoxCoords = new Point2D(box.getCoordsX(), box.getCoordsY());
                box.notifyObservers(EventKind.GAMEBOX_BUTTON_CLICKED);
            }
        });
    }

    public void setVisible(boolean value) {
        if (boxButtonSprites != null)
            for (GameBoxButtonSprite box : boxButtonSprites)
                box.setImageViewVisible(value);
    }

    @Override
    public void render() {
        renderBoxes();
        renderSigns();
    }

    private void renderBoxes() {
        for (GameBoxButtonSprite box : boxButtonSprites)
            box.render();
    }

    private void renderSigns() {
        for (SignButtonSprite sign : signButtonSprites)
            sign.render();
    }

    @Override
    public void update() {
        updateBoxes();
        updateSignButtons();
    }

    private void updateBoxes() {
        for (GameBoxButtonSprite box : boxButtonSprites)
            box.update();
    }

    private void updateSignButtons() {
        for (SignButtonSprite sign : signButtonSprites)
            sign.update();
    }

    public Point2D getLastChosenBoxCoords() {
        return lastChosenBoxCoords;
    }
}
