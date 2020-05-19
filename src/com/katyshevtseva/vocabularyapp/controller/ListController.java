package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.BUTTON_IMAGE_SIZE;
import static com.katyshevtseva.vocabularyapp.utils.Constants.PLUS_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.setImageOnButton;

public class ListController {
    private static String listName;
    private List<SelectableEntry> selectableEntries;
    @FXML
    private Button moveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<SelectableEntry> table;
    @FXML
    private TableColumn<SelectableEntry, String> wordColumn;
    @FXML
    private TableColumn<SelectableEntry, String> translationColumn;
    @FXML
    private TableColumn<SelectableEntry, Number> countColumn;
    @FXML
    private TableColumn<SelectableEntry, Integer> levelColumn;
    @FXML
    private TableColumn<SelectableEntry, Boolean> checkBoxColumn;
    @FXML
    private Button addWordButton;

    static void showWordList(String listToShow) {
        listName = listToShow;
        WindowCreator.getInstance().createListWindow(listName);
    }

    @FXML
    private void initialize() {
        tuneColumns();
        table.setEditable(true);
        setRowClickListener();
        setImageOnButton(PLUS_IMAGE_NAME, addWordButton, BUTTON_IMAGE_SIZE);
        moveButton.setVisible(false);
        deleteButton.setVisible(false);
        updateTable();
    }

    private void tuneColumns() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(
                table.getItems().indexOf(column.getValue()) + 1));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        checkBoxColumn.setCellValueFactory(param -> {
            SelectableEntry selectableEntry = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(selectableEntry.isSelected());
            booleanProperty.addListener((observable, oldValue, newValue) -> {
                selectableEntry.setSelected(newValue);
                moveButton.setVisible(atLeastOneWordSelected());
                deleteButton.setVisible(atLeastOneWordSelected());
            });
            return booleanProperty;
        });
        checkBoxColumn.setCellFactory(param -> {
            CheckBoxTableCell<SelectableEntry, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    private boolean atLeastOneWordSelected() {
        for (SelectableEntry selectableEntry : selectableEntries) {
            if (selectableEntry.isSelected())
                return true;
        }
        return false;
    }

    private void setRowClickListener() {
        table.setRowFactory(tv -> {
            TableRow<SelectableEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    SelectableEntry chosenEntry = row.getItem();
                    WordChangeController.changeWord(chosenEntry.getEntry(), this);
                }
            });
            return row;
        });
    }

    void updateTable() {
        List<Entry> entries = MainController.getDataBase().getEntriesByListName(listName);
        ObservableList<SelectableEntry> words = FXCollections.observableArrayList();
        selectableEntries = convertEntriesToSelectableEntries(entries);
        words.addAll(selectableEntries);
        table.setItems(words);
    }

    private List<SelectableEntry> convertEntriesToSelectableEntries(List<Entry> entries) {
        List<SelectableEntry> selectableEntries = new ArrayList<>();
        for (Entry entry : entries) {
            selectableEntries.add(new SelectableEntry(entry));
        }
        return selectableEntries;
    }

    @FXML
    private void addWordButtonListener() {
        WordAddingController.addWord(this);
    }

    @FXML
    private void renameButtonListener() {
        ListRenameController.renameList(this);
    }

    @FXML
    private void moveButtonListener() {
    }

    @FXML
    private void deleteButtonListener() {
    }

    String getListName() {
        return listName;
    }

    TableView<SelectableEntry> getTable() {
        return table;
    }

    public class SelectableEntry {
        private Entry entry;
        private boolean selected;

        SelectableEntry(Entry entry) {
            this.entry = entry;
            selected = false;
        }

        Entry getEntry() {
            return entry;
        }

        boolean isSelected() {
            return selected;
        }

        void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getWord() {
            return entry.getWord();
        }

        public String getTranslation() {
            return entry.getTranslation();
        }

        public int getLevel() {
            return entry.getLevel();
        }
    }
}
