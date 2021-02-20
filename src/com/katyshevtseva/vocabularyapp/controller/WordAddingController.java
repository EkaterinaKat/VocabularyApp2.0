package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.KeyboardLayoutManager;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WordAddingController {
    private static ListController listController;
    @FXML
    public Button addButton;
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField translationTextField;

    static void addWord(ListController controller) {
        listController = controller;
        WindowCreator.getInstance().createWordInputWindow();
    }

    @FXML
    private void initialize() {
        addButton.setDisable(true);
        wordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            wordTextField.setText(KeyboardLayoutManager.changeToEng(newValue));
            addButton.setDisable(oneOfFieldIsEmpty());
        });
        translationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            translationTextField.setText(KeyboardLayoutManager.changeToRus(newValue));
            addButton.setDisable(oneOfFieldIsEmpty());
        });
    }

    private boolean oneOfFieldIsEmpty() {
        return wordTextFieldIsEmpty() || translationTextFieldIsEmpty();
    }

    private boolean wordTextFieldIsEmpty() {
        return wordTextField.getText().trim().equals("");
    }

    private boolean translationTextFieldIsEmpty() {
        return translationTextField.getText().trim().equals("");
    }

    @FXML
    private void addButtonListener() {
        confirmAdding();
    }

    private void confirmAdding() {
        MainController.getDataBase().addEntry(wordTextField.getText().trim(),
                translationTextField.getText().trim(), listController.getListName());
        MainController.getDataBase().incrementTodayAddingStatistics();
        wordTextField.clear();
        translationTextField.clear();
        listController.updateTable();
        wordTextField.requestFocus();
    }

    @FXML
    private void enterInWordFieldListener() {
        if (!wordTextFieldIsEmpty())
            translationTextField.requestFocus();
    }

    @FXML
    private void enterInTranslationFieldListener() {
        if (!oneOfFieldIsEmpty())
            confirmAdding();
    }
}
