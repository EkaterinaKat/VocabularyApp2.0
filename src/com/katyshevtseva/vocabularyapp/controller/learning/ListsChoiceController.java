package com.katyshevtseva.vocabularyapp.controller.learning;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListsChoiceController {
    private static LearningTuner learningTuner;
    private static Map<String, Boolean> listsToChooseFrom;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private GridPane placementForCheckBoxes = new GridPane();
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button doneButton;

    static void startChoosing(LearningTuner tuner, Map<String, Boolean> lists) {
        learningTuner = tuner;
        listsToChooseFrom = lists;
        WindowCreator.getInstance().createListsChoiceWindow();
    }

    @FXML
    public void initialize() {
        doneButton.setDisable(true);
        tunePlacementForCheckBoxes();
        int rowCount = 0;
        for (Map.Entry list : listsToChooseFrom.entrySet()) {
            placementForCheckBoxes.add(getCheckBox(list), 0, rowCount);
            rowCount++;
        }
    }

    private void tunePlacementForCheckBoxes() {
        scrollPane.setContent(placementForCheckBoxes);
        placementForCheckBoxes.setVgap(10);
    }

    private CheckBox getCheckBox(Map.Entry list) {
        CheckBox checkBox = new CheckBox(list.getKey().toString());
        checkBox.setDisable(!(boolean) list.getValue());
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
        learningTuner.finishListsChoosing(getChosenItems());
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.close();
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
