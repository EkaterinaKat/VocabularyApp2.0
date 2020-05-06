package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowCreator.getInstance().createMainWindow();
    }

    public void createWordList(MouseEvent mouseEvent) {
    }

    public void learnWords(MouseEvent mouseEvent) {
    }

    public void searchWord(MouseEvent mouseEvent) {
    }

    public void about(MouseEvent mouseEvent) {
    }
}
