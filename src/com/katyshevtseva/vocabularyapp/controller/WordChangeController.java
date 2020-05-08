package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.Translator;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WordChangeController {
    private static Entry entry;
    private static ListController listController;
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField translationTextField;
    @FXML
    private TextArea helpTextArea;
    @FXML
    private Button doneButton;

    static void changeWord(Entry entryToChange, ListController controller) {
        entry = entryToChange;
        listController = controller;
        WindowCreator.getInstance().createWordChangeWindow(entryToChange.getWord());
    }

    @FXML
    public void initialize() {
        wordTextField.setText(entry.getWord());
        translationTextField.setText(entry.getTranslation());
        helpTextArea.setText(entry.getHelp());
        wordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            wordTextField.setText(Translator.translateToEng(newValue));
            doneButton.setDisable(wordOrTranslationFieldIsEmpty());
        });
        translationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            translationTextField.setText(Translator.translateToRus(newValue));
            doneButton.setDisable(wordOrTranslationFieldIsEmpty());
        });
    }

    private boolean wordOrTranslationFieldIsEmpty() {
        return wordTextField.getText().trim().equals("") || translationTextField.getText().trim().equals("");
    }

    public void deleteButtonListener() { //todo сделать красной эту кнопку
        WordDeletionController.deleteWord(this);
    }

    public void doneButtonListener() {
    }

    void deleteWord() {
        MainController.getDataBase().deleteWord(entry);
        listController.updateTable();
        Stage stage = (Stage) wordTextField.getScene().getWindow();
        stage.close();
    }
}