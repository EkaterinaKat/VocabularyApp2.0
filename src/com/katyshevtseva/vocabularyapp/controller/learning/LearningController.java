package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.controller.MessageController;
import com.katyshevtseva.vocabularyapp.controller.learning.modes.LearningMode;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Collections;
import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

public class LearningController {
    private static LearningMode mode;
    private static List<Entry> entries;
    private int wordCount;
    @FXML
    private Label wordLabel;
    @FXML
    private Label translationLabel;
    @FXML
    private Label countLabel;
    @FXML
    private Button showTranslationButton;
    @FXML
    private Label levelLabel;
    @FXML
    private Button okButton;
    @FXML
    private Button notOkButton;

    static void startLearning(List<Entry> entriesToLearn, LearningMode learningMode) {
        entries = entriesToLearn;
        mode = learningMode;
        WindowCreator.getInstance().createLearningWindow();
    }

    @FXML
    void initialize() {
        Collections.shuffle(entries);
        wordCount = -1;
        nextWord();
        Utils.setImageOnButton(TICK_IMAGE_NAME, okButton, LEARNING_BUTTONS_IMAGE_SIZE);
        Utils.setImageOnButton(RED_CROSS_IMAGE_NAME, notOkButton, LEARNING_BUTTONS_IMAGE_SIZE);
    }

    private void nextWord() {
        wordCount++;
        if (wordCount < entries.size()) {
            tuneLabelsForNewWord();
            tuneButtonsForNextWord();
        } else {
            finishLearning();
        }
    }

    private void tuneLabelsForNewWord() {
        mode.setWordLabelText(wordLabel, getCurrentEntry());
        countLabel.setText(String.format("%s/%s", wordCount + 1, entries.size()));
        levelLabel.setText("Word level: " + getCurrentEntry().getLevel());
        translationLabel.setText("");
    }

    private Entry getCurrentEntry() {
        return entries.get(wordCount);
    }

    private void tuneButtonsForNextWord() {
        showTranslationButton.setDisable(false);
        okButton.setDisable(true);
        notOkButton.setDisable(true);
    }

    private void finishLearning() {
        MessageController.showMessage("Learning is completed!");
        Utils.closeWindowThatContains(wordLabel);
    }

    public void showTranslationButtonListener() {
        mode.setTranslationLabelText(translationLabel, getCurrentEntry());
        showTranslationButton.setDisable(true);
        okButton.setDisable(false);
        notOkButton.setDisable(false);
    }

    public void okButtonListener() {
        int currentLevel = getCurrentEntry().getLevel();
        MainController.getDataBase().changeEntryLevel(getCurrentEntry(), currentLevel + 1);
        nextWord();
    }

    public void notOkButtonListener() {
        int currentLevel = getCurrentEntry().getLevel();
        if (currentLevel != 0) {
            MainController.getDataBase().changeEntryLevel(getCurrentEntry(), currentLevel - 1);
        }
        nextWord();
    }
}
