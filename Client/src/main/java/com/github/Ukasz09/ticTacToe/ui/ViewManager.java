package com.github.Ukasz09.ticTacToe.ui;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;


public class ViewManager {
    private static final String END_GAME_HINT = "You can press ESC to exit game";
    private static final String DEFAULT_FONT_FAMILY = "Helvetica";
    private static final FontWeight DEFAULT_FONT_WEIGHT = FontWeight.BOLD;
    private static final int DEFAULT_FONT_SIZE = 34;
    private static final Color DEFAULT_FONT_COLOR = Color.TAN;
    private static double resolutionX;
    private static double resolutionY;

    private Stage mainStage;
    private Canvas canvas;
    private GraphicsContext gc;
    private Group root;

    private double rightFrameBorder;
    private double bottomFrameBorder;

    private static ViewManager instance;

    //----------------------------------------------------------------------------------------------------------------//
    private ViewManager() {
        //nothing to do
    }

    //----------------------------------------------------------------------------------------------------------------//
    public static ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();
        return instance;
    }

    public void initialize(String title, boolean fullScreen, EventHandler<KeyEvent> exitBtnHandler) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        resolutionX = screenSize.getWidth();
        resolutionY = screenSize.getHeight();
        initializeMainStage(title, fullScreen);
        root = new Group();
        setMainStageScene();
        addCanvas();
        gc = canvas.getGraphicsContext2D();
        setStartedGraphicsContextProperties();
        initializeWindowBoundary(canvas);
        addExitButtonHandler(exitBtnHandler);
    }

    private void addExitButtonHandler(EventHandler<KeyEvent> eventHandler) {
        mainStage.addEventHandler(KeyEvent.KEY_RELEASED, eventHandler);
    }

    public void closeMainStage() {
        mainStage.close();
    }

    private void initializeMainStage(String title, boolean fullScreen) {
        mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setWidth(resolutionX);
        mainStage.setHeight(resolutionY);
        mainStage.setFullScreen(fullScreen);
        mainStage.setFullScreenExitHint(END_GAME_HINT);
    }

    private void setMainStageScene() {
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
    }

    private void addCanvas() {
        canvas = new Canvas(mainStage.getWidth(), mainStage.getHeight());
        root.getChildren().add(canvas);
    }

    private void setStartedGraphicsContextProperties() {
        setFillColor(DEFAULT_FONT_COLOR);
        setDefaultFont();
    }

    public void setFillColor(Color color) {
        gc.setFill(color);
    }

    private void setDefaultFont() {
        setFont(DEFAULT_FONT_FAMILY, DEFAULT_FONT_WEIGHT, DEFAULT_FONT_SIZE, DEFAULT_FONT_COLOR);
    }

    private void setFont(String family, FontWeight weight, int size, Color color) {
        try {
            Font font = Font.font(family, weight, size);
            setFont(font, color);
        } catch (Exception e) {
            setDefaultFont();
        }
    }

    private void setFont(Font font, Color color) {
        gc.setFont(font);
        gc.setFill(color);
    }

    private void initializeWindowBoundary(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();
        rightFrameBorder = bounds.getMaxX();
        bottomFrameBorder = bounds.getMaxY();
    }

    public void addNode(Node iv) {
        root.getChildren().add(iv);
    }

    public void removeNode(Node iv) {
        root.getChildren().remove(iv);
    }

    public void clearActionNodes() {
        int nodesInRootQty = root.getChildren().size();
        root.getChildren().remove(0, nodesInRootQty);
        root.getChildren().add(canvas);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public GraphicsContext getGraphicContext() {
        return gc;
    }

    public double getRightFrameBorder() {
        return rightFrameBorder;
    }

    public double getBottomFrameBorder() {
        return bottomFrameBorder;
    }

    /**
     * @param heightToScreenProportion - height to screen proportion between <0;1>
     */
    public double getScaledHeight(double heightToScreenProportion) {
        return heightToScreenProportion * bottomFrameBorder;
    }

    /**
     * @param widthToScreenProportion - width to screen proportion between <0;1>
     */
    public double getScaledWidth(double widthToScreenProportion) {
        return widthToScreenProportion * rightFrameBorder;
    }
}

