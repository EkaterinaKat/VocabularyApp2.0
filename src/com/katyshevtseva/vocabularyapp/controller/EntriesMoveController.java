package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class EntriesMoveController {
    private static ListController listController;
    private static List<Entry> entries;
    private ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private GridPane placementForRadioButtons;

    static void chooseListAndMove(List<Entry> entriesToMove, ListController controller) {
        entries = entriesToMove;
        listController = controller;
        WindowCreator.getInstance().createEntryMoveWindow();
    }

    @FXML
    private void initialize() {
        tunePlacementForCheckBoxes();
        placeRadioButtons();
    }

    private void tunePlacementForCheckBoxes() {
        placementForRadioButtons.setVgap(10);
    }

    private void placeRadioButtons() {
        int rowCount = 0;
        List<String> catalogue = MainController.getDataBase().getCatalogue();
        for (String listName : catalogue) {
            placementForRadioButtons.add(getRadioButton(listName), 0, rowCount);
            rowCount++;
        }
    }

    private RadioButton getRadioButton(String listName) {
        RadioButton radioButton = new RadioButton(listName);
        radioButton.setToggleGroup(toggleGroup);
        if (listName.equals(listController.getListName()))
            radioButton.fire();
        return radioButton;
    }

    @FXML
    private void moveButtonListener() {
        moveEntries();
        listController.updateTable();
        listController.hideWordManipulationButton();
        closeWindowThatContains(placementForRadioButtons);
    }

    private void moveEntries() {
        DataBase dataBase = MainController.getDataBase();
        String chosenList = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
        for (Entry entry : entries) {
            dataBase.moveEntry(entry, chosenList);
        }
    }
}
