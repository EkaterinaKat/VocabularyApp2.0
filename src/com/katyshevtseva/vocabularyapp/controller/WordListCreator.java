package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WordListCreator {
    @FXML
    public Button okButton;
    @FXML
    private TextField nameInputField;

    public static void createWordList() {
        WindowCreator.getInstance().createWordListCreationWindow();
    }

    @FXML
    public void initialize() {
        okButton.setDisable(true);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                okButton.setDisable(nameInputFieldIsEmpty()));
    }

    public void okButtonListener() {
        MainController.getDataBase().createList(nameInputField.getText().trim());
        CatalogueTuner.getInstance().updateCatalogue();
        nameInputField.clear();
        Stage stage = (Stage) nameInputField.getScene().getWindow();
        stage.close();
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }
}
