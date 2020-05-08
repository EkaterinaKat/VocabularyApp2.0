package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Translator;
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
    private TextField translationTextField; //todo здесь и везде они должны быть private

    static void addWord(ListController controller) {
        listController = controller;
        WindowCreator.getInstance().createWordInputWindow();
    }

    @FXML
    public void initialize() {
        addButton.setDisable(true);
        wordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            wordTextField.setText(Translator.translateToEng(newValue));
            addButton.setDisable(oneOfFieldIsEmpty());
        });
        translationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            translationTextField.setText(Translator.translateToRus(newValue));
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

    public void addButtonListener() {
        confirmAdding();
    }

    private void confirmAdding() {
        MainController.getDataBase().addWord(listController.getListName(),
                wordTextField.getText().trim(), translationTextField.getText().trim());
        wordTextField.clear();
        translationTextField.clear();
        listController.updateTable();
        wordTextField.requestFocus();
    }

    public void enterInWordFieldListener() {
        if (!wordTextFieldIsEmpty())
            translationTextField.requestFocus();
    }

    public void enterInTranslationFieldListener() {
        if (!oneOfFieldIsEmpty())
            confirmAdding();
    }
}
