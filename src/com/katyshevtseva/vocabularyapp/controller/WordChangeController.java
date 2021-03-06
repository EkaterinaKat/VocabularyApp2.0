package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.KeyboardLayoutManager;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class WordChangeController {
    private static Entry entry;
    private static ListController listController;
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField translationTextField;
    @FXML
    private Button doneButton;

    static void changeWord(Entry entryToChange, ListController controller) {
        entry = entryToChange;
        listController = controller;
        WindowCreator.getInstance().createWordChangeWindow(entryToChange.getWord());
    }

    @FXML
    private void initialize() {
        wordTextField.setText(entry.getWord());
        translationTextField.setText(entry.getTranslation());
        wordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            wordTextField.setText(KeyboardLayoutManager.changeToEng(newValue));
            doneButton.setDisable(oneOfFieldsIsEmpty());
        });
        translationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            translationTextField.setText(KeyboardLayoutManager.changeToRus(newValue));
            doneButton.setDisable(oneOfFieldsIsEmpty());
        });
    }

    private boolean oneOfFieldsIsEmpty() {
        return wordTextField.getText().trim().equals("") || translationTextField.getText().trim().equals("");
    }

    @FXML
    private void doneButtonListener() {
        MainController.getDataBase().editEntry(entry, wordTextField.getText().trim(), translationTextField.getText().trim());
        listController.updateTable();
        listController.hideWordManipulationButton();
        closeWindowThatContains(wordTextField);
    }
}
