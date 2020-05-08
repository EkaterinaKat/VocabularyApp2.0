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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

public class ListController {
    private static String listName;
    @FXML
    public TableView<Entry> table;
    @FXML
    public TableColumn<Entry, String> wordColumn;
    @FXML
    public TableColumn<Entry, String> translationColumn;
    @FXML
    public TableColumn<Entry, Number> countColumn;
    @FXML
    public TableColumn<Entry, Integer> levelColumn;
    @FXML
    public TableColumn<Entry, Integer> helpColumn;
    @FXML
    public Button addWordButton;

    public static void showWordList(String listToShow) {
        listName = listToShow;
        WindowCreator.getInstance().createListWindow(listName);
    }

    @FXML
    private void initialize() {
        tuneTable();
        setRowClickListener();
        updateTable();
        addImageOnButton();
    }

    private void tuneTable() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        helpColumn.setCellValueFactory(new PropertyValueFactory<>("help"));
    }

    private void setRowClickListener() {
        table.setRowFactory(tv -> {
            TableRow<Entry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Entry chosenEntry = row.getItem();
                    createClickOnWordWindow(chosenEntry);
                }
            });
            return row;
        });
    }

    private void updateTable() {
        List<Entry> list = MainController.getDataBase().getList(listName);
        ObservableList<Entry> words = FXCollections.observableArrayList();
        words.addAll(list);
        table.setItems(words);
    }

    private void addImageOnButton() {
        //добавляем полюсик на кнопочку   //todo мб когда-нибудь вынесем это в утилитные методы
        Image image = new Image(IMAGES_PATH + PLUS_IMAGE_NAME);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(BUTTON_IMAGE_SIZE);
        imageView.setFitWidth(BUTTON_IMAGE_SIZE);
        addWordButton.graphicProperty().setValue(imageView);
    }

    public void addWordButtonListener() {

    }

    private void createClickOnWordWindow(Entry entry) {

    }

}
