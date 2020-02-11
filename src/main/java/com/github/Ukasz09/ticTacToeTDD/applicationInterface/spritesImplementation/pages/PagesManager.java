package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.AvatarChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.LabelPane;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.NameChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.SignChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage.GamePage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GamePage gamePanel;
    private NameChoosePage nameChoosePage;
    private AvatarChoosePage avatarChoosePage;
    private SignChoosePage signChoosePanel;

    public PagesManager() {
        observers = new HashSet<>();
        initializeNameChoosePage();
        initializeAvatarsChoosePage();
        initializeSignChoosePage();
        initializeGamePanel();
    }

    private void initializeNameChoosePage() {
        nameChoosePage = new NameChoosePage();
        nameChoosePage.attachObserver(this);
        nameChoosePage.setVisible(false);
    }

    //todo: tmp hard name
    private void initializeAvatarsChoosePage() {
        avatarChoosePage = new AvatarChoosePage("unknown");
        avatarChoosePage.attachObserver(this);
        avatarChoosePage.setVisible(false);
    }

    private void initializeSignChoosePage() {
        signChoosePanel = new SignChoosePage();
        signChoosePanel.attachObserver(this);
        signChoosePanel.setVisible(false);
    }

    private void initializeGamePanel() {
        gamePanel = new GamePage(new GameBackground());
        gamePanel.setVisible(false);
    }

    public void showHomePage() {
        setActualSceneVisible(false);
        setSceneToHomePage();
        actualScene.setVisible(true);
    }

    private void setSceneToHomePage() {
        actualScene = nameChoosePage;
    }

    public void showAvatarChoosePage(String firstPlayerNick) {
        avatarChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
        setActualSceneVisible(false);
        actualScene = avatarChoosePage;
        actualScene.setVisible(true);
    }

    public void setActualInitializedPlayerNick(String firstPlayerNick){
        avatarChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
    }

    public void showSignChoosePage() {
        setActualSceneVisible(false);
        actualScene = signChoosePanel;
        actualScene.setVisible(true);
    }

    public void showGamePage() {
        setActualSceneVisible(false);
        gamePanel.showGameBoard(true);
        actualScene = gamePanel;
        actualScene.setVisible(true);
    }

    private void setActualSceneVisible(boolean value) {
        if (actualScene != null)
            actualScene.setVisible(value);
    }

    public IDrawingGraphic getActualScene() {
        return actualScene;
    }

    public String getLastChosenCorrectName() {
        return nameChoosePage.getLastChosenCorrectName();
    }

    public ImageView getLastChosenAvatar() {
        return avatarChoosePage.getChosenImage();
    }

    public ImageSheetProperty getLastChosenSignSheet() {
        return signChoosePanel.getLastChosenSign();
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        notifyObservers(eventKind);
    }

    @Override
    public void attachObserver(IEventKindObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IEventKindObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventKind eventKind) {
        for (IEventKindObserver observer : observers)
            observer.updateObserver(eventKind);
    }
}
