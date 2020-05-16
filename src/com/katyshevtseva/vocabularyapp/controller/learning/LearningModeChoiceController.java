package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class LearningModeChoiceController {
    private static LearningTuner learningTuner;
    @FXML
    private Button button;

    static void startChoosing(LearningTuner tuner) {
        learningTuner = tuner;
        WindowCreator.getInstance().createLearningModeChoiceWindow();
    }

    @FXML
    private void engToRusButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.ENG_TO_RUS);
        closeWindowThatContains(button);
    }

    @FXML
    private void rusToEngButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.RUS_TO_ENG);
        closeWindowThatContains(button);
    }

    @FXML
    private void spellingButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.SPELLING);
        closeWindowThatContains(button);
    }
}
