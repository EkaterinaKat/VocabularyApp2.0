package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;
import static com.katyshevtseva.vocabularyapp.utils.Constants.QUESTION_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class ListDeletionController {
    private static String listToDelete;
    @FXML
    private ImageView imageView;

    static void deleteWordList(String listName) {
        listToDelete = listName;
        WindowCreator.getInstance().createListDeletionWindow();
    }

    @FXML
    private void initialize() {
        Image image = new Image(IMAGES_PATH + QUESTION_IMAGE_NAME);
        imageView.setImage(image);
    }

    @FXML
    private void deleteButtonListener() {
        MainController.getDataBase().deleteList(listToDelete);
        CatalogueTuner.getInstance().updateCatalogue();
        closeWindowThatContains(imageView);
    }

    @FXML
    private void cancelButtonListener() {
        closeWindowThatContains(imageView);
    }
}
