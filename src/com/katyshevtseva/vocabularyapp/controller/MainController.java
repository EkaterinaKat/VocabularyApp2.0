package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.utils.JDBCDataBase;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainController extends Application {
    private DataBase dataBase;
    @FXML
    public ScrollPane cataloguePlacement;

    @Override
    public void start(Stage primaryStage) {
        WindowCreator.getInstance().createMainWindow();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBCDataBase.getInstance().disconnect();
    }

    @FXML
    public void initialize() {
        dataBase = JDBCDataBase.getInstance();
        CatalogueTuner.create(dataBase, cataloguePlacement);
        CatalogueTuner.getInstance().updateCatalogue();
    }

    public void createWordList() {
    }

    public void learnWords() {
    }

    public void searchWord() {
    }

    public void about() {
    }
}
