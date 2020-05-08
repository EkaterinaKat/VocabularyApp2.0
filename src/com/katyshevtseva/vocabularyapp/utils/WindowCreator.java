package com.katyshevtseva.vocabularyapp.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

public class WindowCreator {
    private static final WindowCreator instance = new WindowCreator();

    public static WindowCreator getInstance() {
        return instance;
    }

    private WindowCreator() {
    }

    public void createMainWindow() {
        Stage stage = getStage("main.fxml", MAIN_WINDOW_TITLE,
                MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT, false);
        stage.show();
    }

    public void createListCreationWindow() {
        createModalWindow("list_creation.fxml",
                LIST_CREATION_WINDOW_TITLE, LIST_CREATION_WINDOW_WIDTH, LIST_CREATION_WINDOW_HEIGHT, false);
    }

    public void createListDeletionWindow(){
        createModalWindow("list_deletion.fxml",
                LIST_DELETION_WINDOW_TITLE, LIST_DELETION_WINDOW_WIDTH, LIST_DELETION_WINDOW_HEIGHT, false);
    }

    private void createModalWindow(String fxmlName, String title, int width, int height, boolean stretchable) {
        Stage stage = getStage(fxmlName, title, width, height, stretchable);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private Stage getStage(String fxmlName, String title, int width, int height, boolean stretchable) {
        Stage stage = new Stage();
        stage.setTitle(title);
        if (!stretchable) {
            stage.setMinHeight(height);
            stage.setMaxHeight(height);
            stage.setMinWidth(width);
            stage.setMaxWidth(width);
        }
        stage.setScene(new Scene(getParent(fxmlName), width, height));
        String logoURL = IMAGES_PATH + LOGO_IMAGE_NAME;
        stage.getIcons().add(new Image(logoURL));
        setClosingWithEscape(stage);
        return stage;
    }

    private Parent getParent(String fxmlName) {
        String fullFxmlName = FXML_PATH + fxmlName;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fullFxmlName));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    private void setClosingWithEscape(Stage stage) {
        stage.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
    }
}
