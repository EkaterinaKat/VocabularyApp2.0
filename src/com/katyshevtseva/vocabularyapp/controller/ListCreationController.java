package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.katyshevtseva.vocabularyapp.model.DataManager.listWithThisNameExists;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class ListCreationController {
    @FXML
    private Button createButton;
    @FXML
    private TextField nameInputField;

    static void createWordList() {
        WindowCreator.getInstance().createListCreationWindow();
    }

    @FXML
    private void initialize() {
        createButton.setDisable(true);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(nameInputFieldIsEmpty()));
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }

    @FXML
    private void okButtonListener() {
        if (listWithThisNameExists(nameInputField.getText().trim())) { //todo поправить если надо будет
            MessageController.showMessage("List with this name already exists");
        } else {
            MainController.getDataBase().createList(nameInputField.getText().trim());
            CatalogueTuner.getInstance().updateCatalogue();
            nameInputField.clear();
            closeWindowThatContains(createButton);
        }
    }
}
