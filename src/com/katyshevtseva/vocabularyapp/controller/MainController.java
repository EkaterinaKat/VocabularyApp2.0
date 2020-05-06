package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.utils.JDBCDataBase;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController extends Application {
    private DataBase dataBase;

    @Override
    public void start(Stage primaryStage) throws Exception {
        dataBase = JDBCDataBase.getInstance();
        WindowCreator.getInstance().createMainWindow();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBCDataBase.getInstance().disconnect();
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
