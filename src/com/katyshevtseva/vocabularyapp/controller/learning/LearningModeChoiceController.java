package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LearningModeChoiceController {
    private static LearningTuner learningTuner;
    @FXML
    private Button button;

    static void startChoosing(LearningTuner tuner) {
        learningTuner = tuner;
        WindowCreator.getInstance().createLearningModeChoiceWindow();
    }

    public void engToRusButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.LearningMode.ENG_TO_RUS);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void rusToEngButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.LearningMode.RUS_TO_ENG);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void spellingButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.LearningMode.SPELLING);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
