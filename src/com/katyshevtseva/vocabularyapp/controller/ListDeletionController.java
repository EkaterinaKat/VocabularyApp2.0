package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;
import static com.katyshevtseva.vocabularyapp.utils.Constants.QUESTION_IMAGE_NAME;

public class ListDeletionController {
    private static String listToDelete;
    @FXML
    public ImageView imageView;

    public static void deleteWordList(String listName) {
        listToDelete = listName;
        WindowCreator.getInstance().createListDeletionWindow();
    }

    @FXML
    public void initialize() {
        Image image = new Image(IMAGES_PATH + QUESTION_IMAGE_NAME);
        imageView.setImage(image);
    }

    public void delete() {
        MainController.getDataBase().deleteList(listToDelete);
        CatalogueTuner.getInstance().updateCatalogue();
        Stage stage = (Stage) imageView.getScene().getWindow();  //todo было бы здорово создать утилитный закрывающий метод
        stage.close();
    }

    public void cancel() {
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }
}
