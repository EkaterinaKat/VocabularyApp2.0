package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.controller.MainController;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.model.LearningTunerHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LearningTuner {
    private LearningMode chosenMode;
    private LearningTunerHelper helper;
    private List<String> chosenLists;

    public LearningTuner() {
        helper = new LearningTunerHelper(MainController.getDataBase());
    }

    enum LearningMode {
        RUS_TO_ENG, ENG_TO_RUS, SPELLING
    }

    public void startTuning() {
        LearningModeChoiceController.startChoosing(this);
    }

    void finishModeChoosing(LearningMode learningMode) {
        chosenMode = LearningMode.RUS_TO_ENG;
        Map<String, Boolean> listsToChooseFrom = helper.getListsToChooseFrom();
        ListsChoiceController.startChoosing(this, listsToChooseFrom);
    }

    void finishListsChoosing(List<String> chosenLists) {
        this.chosenLists = chosenLists;
        Set<Integer> levelsToChoose = helper.getLevelsToChooseFrom(chosenLists);
        LevelsChoiceController.startChoosing(this, levelsToChoose);
    }

    void finishLevelsChoosing(List<Integer> chosenLevels) {
        List<Entry> entriesToLearn = helper.getEntries(chosenLists, chosenLevels);
        LearningController.startLearning(entriesToLearn);
    }
}
