package com.katyshevtseva.vocabularyapp.controller;


import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.model.SearchHelper;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class WordSearchController {
    private SearchHelper helper;
    @FXML
    private TextField textField;
    @FXML
    private TableView<Entry> table;
    @FXML
    private TableColumn<Entry, String> wordColumn;
    @FXML
    private TableColumn<Entry, String> translationColumn;
    @FXML
    private TableColumn<Entry, Integer> levelColumn;
    @FXML
    private TableColumn<Entry, String> listNameColumn;

    static void startWordSearch() {
        WindowCreator.getInstance().createWordSearchWindow();
    }

    @FXML
    private void initialize() {
        tuneTable();
        helper = new SearchHelper();
        fillTable(textField.getText());
        textField.textProperty().addListener((observable, oldValue, newValue) -> fillTable(textField.getText()));
    }

    private void tuneTable() {
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        listNameColumn.setCellValueFactory(new PropertyValueFactory<>("listName"));
    }

    private void fillTable(String inputString) {
        List<Entry> list = helper.searchForEntries(inputString);
        ObservableList<Entry> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        table.setItems(observableList);
    }
}
