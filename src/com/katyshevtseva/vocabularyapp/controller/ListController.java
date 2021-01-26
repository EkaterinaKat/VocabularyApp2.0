package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.model.LearningTunerHelper;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.BUTTON_IMAGE_SIZE;
import static com.katyshevtseva.vocabularyapp.utils.Constants.PLUS_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.setImageOnButton;

public class ListController implements AnswerReceiver {
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
    private TableColumn<SelectableEntry, String> dateColumn;
    @FXML
    private Button addWordButton;
    @FXML
    private Button editButton;

    static void showWordList(String listToShow) {
        listName = listToShow;
        WindowCreator.getInstance().createListWindow(listName);
    }

    @FXML
    private void initialize() {
        tuneColumns();
        table.setEditable(true);
        setImageOnButton(PLUS_IMAGE_NAME, addWordButton, BUTTON_IMAGE_SIZE);
        hideWordManipulationButton();
        updateTable();
    }

    void hideWordManipulationButton() {
        moveButton.setVisible(false);
        deleteButton.setVisible(false);
        editButton.setVisible(false);
    }

    private void tuneColumns() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(
                table.getItems().indexOf(column.getValue()) + 1));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("lastRepeat"));
        checkBoxColumn.setCellValueFactory(param -> {
            SelectableEntry selectableEntry = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(selectableEntry.isSelected());
            booleanProperty.addListener((observable, oldValue, newValue) -> {
                selectableEntry.setSelected(newValue);
                moveButton.setVisible(atLeastOneWordSelected());
                deleteButton.setVisible(atLeastOneWordSelected());
                editButton.setVisible(oneWordSelected());
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

    private boolean oneWordSelected() {
        return getSelectedEntries().size() == 1;
    }

    private List<Entry> getSelectedEntries() {
        List<Entry> selectedEntries = new ArrayList<>();
        for (SelectableEntry entry : selectableEntries) {
            if (entry.isSelected())
                selectedEntries.add(entry.getEntry());
        }
        return selectedEntries;
    }

    void updateTable() {
        List<Entry> entries = MainController.getDataBase().getEntriesByListName(listName);
        Collections.reverse(entries);
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
    private void editButtonListener() {
        WordChangeController.changeWord(getSelectedEntries().get(0), this);
    }

    @FXML
    private void moveButtonListener() {
        EntriesMoveController.chooseListAndMove(getSelectedEntries(), this);
    }

    @FXML
    private void deleteButtonListener() {
        QuestionController.askQuestion("Are you sure you want to delete the words?", this);
    }

    @Override
    public void receivePositiveAnswer() {
        deleteSelectedWord();
    }

    private void deleteSelectedWord() {
        for (Entry entry : getSelectedEntries()) {
            MainController.getDataBase().deleteEntry(entry);
        }
        updateTable();
        hideWordManipulationButton();
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

        public String getLastRepeat() {
            return new SimpleDateFormat("dd.MM.yyyy").format(entry.getLastRepeat()) +
                    String.format(" (%s)", LearningTunerHelper.entryIsRipe(entry));
        }
    }
}
