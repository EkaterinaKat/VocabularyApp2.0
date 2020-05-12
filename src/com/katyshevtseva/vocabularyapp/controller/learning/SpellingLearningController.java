package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.controller.MessageController;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;

public class SpellingLearningController {
    private static List<Entry> entries;
    private int wordCount;
    private StyleHelper helper;
    @FXML
    private Label wordLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Button doneButton;
    @FXML
    private Label resultLabel;
    @FXML
    private Button nextButton;
    @FXML
    private Label levelLabel;
    @FXML
    private Label countLabel;

    static void startLearning(List<Entry> entriesToLearn) {
        entries = entriesToLearn;
        WindowCreator.getInstance().createSpellingLearningWindow();
    }

    @FXML
    void initialize() {
        helper = new StyleHelper();
        answerField.textProperty().addListener((observable, oldValue, newValue) ->
                doneButton.setDisable(textFieldIsEmpty()));
        Collections.shuffle(entries);
        wordCount = -1;
        nextWord();
    }

    private boolean textFieldIsEmpty() {
        return answerField.getText().trim().equals("");
    }

    private void nextWord() {
        wordCount++;

        if (wordCount == (entries.size()) - 1) {
            nextButton.setText("Finish");
        }

        if (wordCount < entries.size()) {
            tuneElementsForNewWord();
        } else {
            finishLearning();
        }
    }

    private void tuneElementsForNewWord() {
        wordLabel.setText(getCurrentEntry().getTranslation());
        answerField.setText("");
        answerField.setEditable(true);
        helper.setOriginalAnswerFieldStyle();
        doneButton.setDisable(true);
        resultLabel.setText("");
        nextButton.setDisable(true);
        countLabel.setText(String.format("%s/%s", wordCount + 1, entries.size()));
        levelLabel.setText("Word level: " + entries.get(wordCount).getLevel());
    }

    private Entry getCurrentEntry() {
        return entries.get(wordCount);
    }

    private void finishLearning() {
        MessageController.showMessage("Learning is completed!");
        Utils.closeWindowThatContains(wordLabel);
    }

    public void enterInTextFieldListener() {
        processAnswer();
    }

    public void doneButtonListener() {
        processAnswer();
    }

    private void processAnswer() {
        int newLevel;
        if (answerIsCorrect(answerField.getText())) {
            showPositiveResult();
            newLevel = getCurrentEntry().getLevel() + 1;
        } else {
            showNegativeResult();
            newLevel = getCurrentEntry().getLevel() == 0 ? 0 : getCurrentEntry().getLevel() - 1;
        }
        MainController.getDataBase().changeEntryLevel(getCurrentEntry(), newLevel);
        levelLabel.setText("Word level: " + newLevel);
        answerField.setEditable(false);
        doneButton.setDisable(true);
        nextButton.setDisable(false);
    }

    private boolean answerIsCorrect(String inputString) {
        return inputString.trim().equalsIgnoreCase(getCurrentEntry().getWord());
    }

    private void showPositiveResult() {
        helper.setPositiveAnswerFieldStyle();
        helper.setPositiveResultLabelStyle();
        resultLabel.setText("Correct!");
    }

    private void showNegativeResult() {
        helper.setNegativeAnswerFieldStyle();
        helper.setNegativeResultLabelStyle();
        resultLabel.setText("Correct spelling: " + getCurrentEntry().getWord());
    }

    public void nextButtonListener() {
        nextWord();
    }

    private class StyleHelper {
        private String originalTextFieldStyle;

        StyleHelper() {
            originalTextFieldStyle = answerField.getStyle();
        }

        void setPositiveAnswerFieldStyle() {
            String positiveStyle = "-fx-font-style: italic; " +
                    "-fx-text-fill: #006400; " +
                    "-fx-control-inner-background: #7CFC00;";
            answerField.setStyle(positiveStyle);
        }

        void setNegativeAnswerFieldStyle() {
            String negativeStyle = "-fx-text-fill: #FFFFFF; " +
                    "-fx-control-inner-background: #C14242;";
            answerField.setStyle(negativeStyle);
        }

        void setOriginalAnswerFieldStyle() {
            answerField.setStyle(originalTextFieldStyle);
        }

        void setPositiveResultLabelStyle() {
            String positiveStyle = "-fx-text-fill: #006400; " +
                    "-fx-font-weight: bold; ";
            resultLabel.setStyle(positiveStyle);
        }

        void setNegativeResultLabelStyle() {
            String negativeStyle = "-fx-text-fill: #C14242; " +
                    "-fx-font-weight: bold; ";
            resultLabel.setStyle(negativeStyle);
        }
    }
}
