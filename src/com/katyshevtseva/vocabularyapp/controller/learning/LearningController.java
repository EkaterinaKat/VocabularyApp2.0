package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.controller.MessageController;
import com.katyshevtseva.vocabularyapp.controller.learning.modes.LearningMode;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;
import static com.katyshevtseva.vocabularyapp.utils.Utils.setImageOnButton;

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
    private void initialize() {
        Collections.shuffle(entries);
        wordCount = -1;
        nextWord();
        setImageOnButton(TICK_IMAGE_NAME, okButton, LEARNING_BUTTONS_IMAGE_SIZE);
        setImageOnButton(RED_CROSS_IMAGE_NAME, notOkButton, LEARNING_BUTTONS_IMAGE_SIZE);
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
        String desc = String.format("Level: %s\nList: %s\nLast repeat: %s", getCurrentEntry().getLevel(),
                getCurrentEntry().getListName(),
                new SimpleDateFormat("dd.MM.yyyy").format(getCurrentEntry().getLastRepeat()));
        levelLabel.setText(desc);
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
        closeWindowThatContains(wordLabel);
    }

    @FXML
    private void showTranslationButtonListener() {
        mode.setTranslationLabelText(translationLabel, getCurrentEntry());
        showTranslationButton.setDisable(true);
        okButton.setDisable(false);
        notOkButton.setDisable(false);
    }

    @FXML
    private void okButtonListener() {
        int currentLevel = getCurrentEntry().getLevel();
        MainController.getDataBase().changeEntryLevel(getCurrentEntry(), currentLevel + 1);
        nextWord();
    }

    @FXML
    private void notOkButtonListener() {
        int currentLevel = getCurrentEntry().getLevel();
        if (currentLevel != 0) {
            MainController.getDataBase().changeEntryLevel(getCurrentEntry(), currentLevel - 1);
        }
        nextWord();
    }
}
