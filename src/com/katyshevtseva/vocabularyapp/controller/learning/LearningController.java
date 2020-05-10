package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.controller.MessageController;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

public class LearningController {
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
    @FXML
    private Button helpButton;
    @FXML
    private Label helpLabel;

    static void startLearning(List<Entry> entriesToLearn) {
        entries = entriesToLearn;
        WindowCreator.getInstance().createWordsLearningWindow();
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
        wordLabel.setText(entries.get(wordCount).getWord());
        String s = String.format("%s/%s", wordCount + 1, entries.size());
        countLabel.setText(s);
        levelLabel.setText("Word level: " + entries.get(wordCount).getLevel());
        helpLabel.setText("");
        translationLabel.setText("");
    }

    private void tuneButtonsForNextWord() {
        showTranslationButton.setDisable(false);
        if ((entries.get(wordCount).getHelp() == null) || (entries.get(wordCount).getLevel() > 3)) {
            helpButton.setDisable(true);
        } else {
            helpButton.setDisable(false);
        }
        okButton.setDisable(true);
        notOkButton.setDisable(true);
    }

    private void finishLearning() {
        MessageController.showMessage("Learning is completed!");
        Stage stage = (Stage) wordLabel.getScene().getWindow();
        stage.close();
    }

    public void helpButtonListener() {
        helpLabel.setText(entries.get(wordCount).getHelp());
        helpButton.setDisable(true);
    }

    public void showTranslationButtonListener() {
        translationLabel.setText(entries.get(wordCount).getTranslation());
        showTranslationButton.setDisable(true);
        okButton.setDisable(false);
        notOkButton.setDisable(false);
    }

    public void okButtonListener() {
        int currentLevel = entries.get(wordCount).getLevel();
        MainController.getDataBase().changeEntryLevel(entries.get(wordCount), currentLevel + 1);
        nextWord();
    }

    public void notOkButtonListener() {
        int currentLevel = entries.get(wordCount).getLevel();
        if (currentLevel != 0) {
            MainController.getDataBase().changeEntryLevel(entries.get(wordCount), currentLevel - 1);
        }
        nextWord();
    }
}
