package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.DataBase.JDBC;
import com.katyshevtseva.vocabularyapp.controller.learning.LearningTuner;
import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainController extends Application {
    private static DataBase dataBase;
    private static boolean connectionToDataBaseFailed = false;
    @FXML
    private Button listCreationButton;
    @FXML
    private Button learningButton;
    @FXML
    private Button searchButton;
    @FXML
    private ScrollPane cataloguePlacement;

    @Override
    public void start(Stage primaryStage) {
        WindowCreator.getInstance().createMainWindow();
        if (connectionToDataBaseFailed) {
            MessageController.showMessage("JDBC not found");
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBC.getInstance().disconnect();
    }

    @FXML
    public void initialize() {
        try {
            dataBase = JDBC.getInstance();
            CatalogueTuner.create(cataloguePlacement);
            CatalogueTuner.getInstance().updateCatalogue();
        } catch (ClassNotFoundException e) {
            connectionToDataBaseFailed = true;
            disableButtons();
            e.printStackTrace();
        }
    }

    private void disableButtons() {
        listCreationButton.setDisable(true);
        learningButton.setDisable(true);
        searchButton.setDisable(true);
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
        WordSearchController.startWordSearch();
    }

    public void about() {
        AboutController.showAbout();
    }
}
