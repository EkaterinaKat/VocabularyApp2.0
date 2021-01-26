package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class ListsChoiceController {
    private static LearningTuner learningTuner;
    private static Map<String, Boolean> listsToChooseFrom;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    @FXML
    public GridPane placementForCheckBoxes;
    @FXML
    private Button doneButton;

    static void startChoosing(LearningTuner tuner, Map<String, Boolean> lists) {
        learningTuner = tuner;
        listsToChooseFrom = lists;
        WindowCreator.getInstance().createListsChoiceWindow();
    }

    @FXML
    private void initialize() {
        doneButton.setDisable(!listsToChooseFrom.values().contains(Boolean.TRUE));
        tunePlacementForCheckBoxes();
        placeCheckBoxes();
    }

    private void tunePlacementForCheckBoxes() {
        placementForCheckBoxes.setVgap(10);
    }

    private void placeCheckBoxes() {
        int rowCount = 0;
        for (Map.Entry list : listsToChooseFrom.entrySet()) {
            placementForCheckBoxes.add(getCheckBox(list), 0, rowCount);
            rowCount++;
        }
    }

    private CheckBox getCheckBox(Map.Entry list) {
        CheckBox checkBox = new CheckBox(list.getKey().toString());
        boolean listContainRipeEntries = (boolean) list.getValue();
        checkBox.setSelected(listContainRipeEntries);
        checkBox.setDisable(!listContainRipeEntries);
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

    @FXML
    private void doneButtonListener() {
        learningTuner.finishListsChoosing(getChosenItems());
        closeWindowThatContains(doneButton);
    }

    private List<String> getChosenItems() {
        List<String> chosenLists = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected())
                chosenLists.add(checkBox.getText());
        }
        return chosenLists;
    }
}
