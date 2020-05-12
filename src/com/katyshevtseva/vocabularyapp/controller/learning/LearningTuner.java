package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.controller.learning.modes.EngToRusMode;
import com.katyshevtseva.vocabularyapp.controller.learning.modes.RusToEngMode;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.model.LearningTunerHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LearningTuner {
    private LearningTunerHelper helper;
    private Mode chosenMode;
    private List<String> chosenLists;
    private List<Integer> chosenLevels;

    public LearningTuner() {
        helper = new LearningTunerHelper(MainController.getDataBase());
    }

    enum Mode {
        RUS_TO_ENG, ENG_TO_RUS, SPELLING
    }

    public void startTuning() {
        LearningModeChoiceController.startChoosing(this);
    }

    void finishModeChoosing(Mode mode) {
        chosenMode = mode;
        Map<String, Boolean> listsToChooseFrom = helper.getListsToChooseFrom();
        ListsChoiceController.startChoosing(this, listsToChooseFrom);
    }

    void finishListsChoosing(List<String> chosenLists) {
        this.chosenLists = chosenLists;
        Set<Integer> levelsToChoose = helper.getLevelsToChooseFrom(chosenLists);
        LevelsChoiceController.startChoosing(this, levelsToChoose);
    }

    void finishLevelsChoosing(List<Integer> chosenLevels) {
        this.chosenLevels = chosenLevels;
        startLearningInChosenMode();
    }

    private void startLearningInChosenMode() {
        List<Entry> entriesToLearn = helper.getEntries(chosenLists, chosenLevels);
        switch (chosenMode) {
            case ENG_TO_RUS:
                LearningController.startLearning(entriesToLearn, new EngToRusMode());
                break;
            case RUS_TO_ENG:
                LearningController.startLearning(entriesToLearn, new RusToEngMode());
                break;
            case SPELLING:
                SpellingLearningController.startLearning(entriesToLearn);
        }
    }
}
