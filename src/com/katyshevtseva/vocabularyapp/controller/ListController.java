package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.BUTTON_IMAGE_SIZE;
import static com.katyshevtseva.vocabularyapp.utils.Constants.PLUS_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.setImageOnButton;

public class ListController {
    private static String listName;
    @FXML
    private TableView<Entry> table;
    @FXML
    private TableColumn<Entry, String> wordColumn;
    @FXML
    private TableColumn<Entry, String> translationColumn;
    @FXML
    private TableColumn<Entry, Number> countColumn;
    @FXML
    private TableColumn<Entry, Integer> levelColumn;
    @FXML
    private Button addWordButton;

    static void showWordList(String listToShow) {
        listName = listToShow;
        WindowCreator.getInstance().createListWindow(listName);
    }

    @FXML
    private void initialize() {
        tuneTable();
        setRowClickListener();
        updateTable();
        setImageOnButton(PLUS_IMAGE_NAME, addWordButton, BUTTON_IMAGE_SIZE);
    }

    private void tuneTable() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
    }

    private void setRowClickListener() {
        table.setRowFactory(tv -> {
            TableRow<Entry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Entry chosenEntry = row.getItem();
                    WordChangeController.changeWord(chosenEntry, this);
                }
            });
            return row;
        });
    }

    void updateTable() {
        List<Entry> list = MainController.getDataBase().getEntriesByListName(listName);
        ObservableList<Entry> words = FXCollections.observableArrayList();
        words.addAll(list);
        table.setItems(words);
    }

    @FXML
    private void addWordButtonListener() {
        WordAddingController.addWord(this);
    }

    @FXML
    private void renameButtonListener() {
        ListRenameController.renameList(this);
    }

    String getListName() {
        return listName;
    }

    TableView<Entry> getTable() {
        return table;
    }
}
