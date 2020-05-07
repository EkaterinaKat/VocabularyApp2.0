package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WordListCreator {
    @FXML
    private TextField nameInputField;

    public static void createWordList() {
        WindowCreator.getInstance().createWordListCreationWindow();
    }

    public void okButtonListener() {
        if (!nameInputFieldIsEmpty()) {
            MainController.getDataBase().createList(nameInputField.getText());
            CatalogueTuner.getInstance().updateCatalogue();
            nameInputField.clear();
            Stage stage = (Stage) nameInputField.getScene().getWindow();
            stage.close();
        } else {
//            MessageController.message = TEXT_FIELD_IS_EMPTY_WARNING;
//            WindowCreator.getInstance().createModalWindow("message_sample.fxml",
//                    WARNING_WINDOW_TITLE, WARNING_WINDOW_WIDTH, WARNING_WINDOW_HEIGHT, false);
        }
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }
}
