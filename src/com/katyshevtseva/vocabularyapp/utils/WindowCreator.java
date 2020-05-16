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
                LIST_CREATION_WINDOW_TITLE, LIST_NAME_INPUT_WINDOW_WIDTH, LIST_NAME_INPUT_WINDOW_HEIGHT, false);
    }

    public void createListRenameWindow() {
        createModalWindow("list_rename.fxml",
                LIST_RENAME_WINDOW_TITLE, LIST_NAME_INPUT_WINDOW_WIDTH, LIST_NAME_INPUT_WINDOW_HEIGHT, false);
    }

    public void createListDeletionWindow() {
        createModalWindow("list_deletion.fxml",
                LIST_DELETION_WINDOW_TITLE, QUESTION_WINDOW_WIDTH, QUESTION_WINDOW_HEIGHT, false);
    }

    public void createListWindow(String listName) {
        createModalWindow("list.fxml",
                listName, WORD_LIST_WINDOW_WIDTH, WORD_LIST_WINDOW_HEIGHT, true);
    }

    public void createWordInputWindow() {
        createModalWindow("word_input.fxml",
                WORD_INPUT_WINDOW_TITLE, WORD_INPUT_WINDOW_WIDTH, WORD_INPUT_WINDOW_HEIGHT, false);
    }

    public void createWordChangeWindow(String word) {
        createModalWindow("word_change.fxml",
                word, WORD_CHANGE_WINDOW_WIDTH, WORD_CHANGE_WINDOW_HEIGHT, false);
    }

    public void createWordDeletionWindow() {
        createModalWindow("word_deletion.fxml",
                WORD_DELETION_WINDOW_TITLE, QUESTION_WINDOW_WIDTH, QUESTION_WINDOW_HEIGHT, false);
    }

    public void createLearningModeChoiceWindow() {
        createModalWindow("learning_mode_choice.fxml",
                MODE_CHOICE_WINDOW_TITLE, MODE_CHOICE_WINDOW_WIDTH, MODE_CHOICE_WINDOW_HEIGHT, false);
    }

    public void createListsChoiceWindow() {
        createModalWindow("lists_choice.fxml",
                LISTS_CHOICE_WINDOW_TITLE, CHOICE_WINDOW_WIDTH, CHOICE_WINDOW_HEIGHT, false);
    }

    public void createLevelsChoiceWindow() {
        createModalWindow("levels_choice.fxml",
                LEVELS_CHOICE_WINDOW_TITLE, CHOICE_WINDOW_WIDTH, CHOICE_WINDOW_HEIGHT, false);
    }

    public void createLearningWindow() {
        createModalWindow("learning.fxml",
                LEARNING_WINDOW_TITLE, LEARNING_WINDOW_WIDTH, LEARNING_WINDOW_HEIGHT, false);
    }

    public void createSpellingLearningWindow() {
        createModalWindow("spelling_learning.fxml",
                LEARNING_WINDOW_TITLE, LEARNING_WINDOW_WIDTH, LEARNING_WINDOW_HEIGHT, false);
    }

    public void createMessageWindow() {
        createModalWindow("message.fxml",
                MESSAGE_WINDOW_TITLE, MESSAGE_WINDOW_WIDTH, MESSAGE_WINDOW_HEIGHT, false);
    }

    public void createAboutWindow() {
        createModalWindow("about.fxml",
                ABOUT_WINDOW_TITLE, ABOUT_WINDOW_WIDTH, ABOUT_WINDOW_HEIGHT, false);
    }

    public void createWordSearchWindow() {
        createModalWindow("word_search.fxml",
                WORD_SEARCH_WINDOW_TITLE, WORD_SEARCH_WINDOW_WIDTH, WORD_SEARCH_WINDOW_HEIGHT, false);
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
