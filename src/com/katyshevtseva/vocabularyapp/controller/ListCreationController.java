package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ListCreationController {
    @FXML
    public Button createButton;
    @FXML
    private TextField nameInputField;

    public static void createWordList() {
        WindowCreator.getInstance().createListCreationWindow();
    }

    @FXML
    public void initialize() {
        createButton.setDisable(true);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(nameInputFieldIsEmpty()));
    }

    public void okButtonListener() {
        MainController.getDataBase().createList(nameInputField.getText().trim());
        CatalogueTuner.getInstance().updateCatalogue();
        nameInputField.clear();
        Utils.closeWindowThatContains(createButton);
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }
}
