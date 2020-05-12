package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LearningModeChoiceController {
    private static LearningTuner learningTuner;
    @FXML
    private Button button;

    static void startChoosing(LearningTuner tuner) {
        learningTuner = tuner;
        WindowCreator.getInstance().createLearningModeChoiceWindow();
    }

    public void engToRusButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.ENG_TO_RUS);
        Utils.closeWindowThatContains(button);
    }

    public void rusToEngButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.RUS_TO_ENG);
        Utils.closeWindowThatContains(button);
    }

    public void spellingButtonListener() {
        learningTuner.finishModeChoosing(LearningTuner.Mode.SPELLING);
        Utils.closeWindowThatContains(button);
    }
}
