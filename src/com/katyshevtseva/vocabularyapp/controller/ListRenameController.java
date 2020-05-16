package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.katyshevtseva.vocabularyapp.model.DataManager.listWithThisNameExists;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class ListRenameController {
    private static ListController listController;
    private static String currentListName;
    @FXML
    private TextField nameInputField;
    @FXML
    private Button createButton;

    static void renameList(ListController controller) {
        listController = controller;
        currentListName = listController.getListName();
        WindowCreator.getInstance().createListRenameWindow();
    }

    @FXML
    private void initialize() {
        nameInputField.setText(currentListName);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(nameInputFieldIsEmpty()));
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }

    @FXML
    private void renameButtonListener() {
        String newName = nameInputField.getText().trim();
        if (listWithThisNameExists(newName)) { //todo поправить если надо будет
            MessageController.showMessage("List with this name already exists");
        } else {
            MainController.getDataBase().renameList(currentListName, newName);
            closeWindowThatContains(listController.getTable());
            CatalogueTuner.getInstance().updateCatalogue();
            nameInputField.clear();
            closeWindowThatContains(createButton);
            ListController.showWordList(newName);
        }
    }
}
