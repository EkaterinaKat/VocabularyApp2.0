package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class ListCreationController {
    @FXML
    private Button createButton;
    @FXML
    private TextField nameInputField;

    static void createWordList() {
        WindowCreator.getInstance().createListCreationWindow();
    }

    @FXML
    public void initialize() {
        createButton.setDisable(true);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(nameInputFieldIsEmpty()));
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }

    public void okButtonListener() {
        if (listWithThisNameExists()) {
            MessageController.showMessage("List with this name already exists");
        } else {
            MainController.getDataBase().createList(nameInputField.getText().trim());
            CatalogueTuner.getInstance().updateCatalogue();
            nameInputField.clear();
            Utils.closeWindowThatContains(createButton);
        }
    }

    private boolean listWithThisNameExists() {
        List<String> listNames = MainController.getDataBase().getCatalogue();
        return listNames.contains(nameInputField.getText().trim());
    }
}
