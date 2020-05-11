package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.controller.learning.LearningTuner;
import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.utils.JDBC;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainController extends Application {
    private static DataBase dataBase;
    @FXML
    public ScrollPane cataloguePlacement;

    @Override
    public void start(Stage primaryStage) {
        WindowCreator.getInstance().createMainWindow();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBC.getInstance().disconnect();
    }

    @FXML
    public void initialize() {
        dataBase = JDBC.getInstance();
        CatalogueTuner.create(cataloguePlacement);
        CatalogueTuner.getInstance().updateCatalogue();
    }

    public static DataBase getDataBase() {
        return dataBase;
    }

    public void createWordList() {
        ListCreationController.createWordList();
    }

    public void learnWords() {
        new LearningTuner().startTuning();
    }

    public void searchWord() {
    }

    public void about() {
    }
}
