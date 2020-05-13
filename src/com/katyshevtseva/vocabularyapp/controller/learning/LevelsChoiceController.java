package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LevelsChoiceController {
    private static LearningTuner learningTuner;
    private static Set<Integer> levelsToChooseFrom;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    @FXML
    private Button doneButton;
    @FXML
    private GridPane placementForCheckBoxes;

    static void startChoosing(LearningTuner tuner, Set<Integer> levels) {
        learningTuner = tuner;
        levelsToChooseFrom = levels;
        WindowCreator.getInstance().createLevelsChoiceWindow();
    }

    @FXML
    public void initialize() {
        doneButton.setDisable(true);
        tunePlacementForCheckBoxes();
        placeCheckBoxes();
    }

    private void tunePlacementForCheckBoxes() {
        placementForCheckBoxes.setVgap(10);
    }

    private void placeCheckBoxes() {
        int rowCount = 0;
        for (Integer level : levelsToChooseFrom) {
            placementForCheckBoxes.add(getCheckBox(level), 0, rowCount);
            rowCount++;
        }
    }

    private CheckBox getCheckBox(Integer level) {
        CheckBox checkBox = new CheckBox(level.toString());
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                doneButton.setDisable(noneOfCheckBoxesAreSelected()));
        checkBoxes.add(checkBox);
        return checkBox;
    }

    private boolean noneOfCheckBoxesAreSelected() {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                return false;
            }
        }
        return true;
    }

    public void doneButtonListener() {
        learningTuner.finishLevelsChoosing(getChosenItems());
        Utils.closeWindowThatContains(doneButton);
    }

    private List<Integer> getChosenItems() {
        List<Integer> chosenLevels = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected())
                chosenLevels.add(Integer.valueOf(checkBox.getText()));
        }
        return chosenLevels;
    }
}
