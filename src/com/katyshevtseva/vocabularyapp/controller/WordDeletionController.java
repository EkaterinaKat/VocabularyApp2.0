package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;
import static com.katyshevtseva.vocabularyapp.utils.Constants.QUESTION_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class WordDeletionController {
    private static WordChangeController wordChangeController;
    @FXML
    private ImageView imageView;

    static void deleteWord(WordChangeController controller) {
        wordChangeController = controller;
        WindowCreator.getInstance().createWordDeletionWindow();
    }

    @FXML
    private void initialize() {
        Image image = new Image(IMAGES_PATH + QUESTION_IMAGE_NAME);
        imageView.setImage(image);
    }

    @FXML
    private void deleteButtonListener() {
        wordChangeController.deleteWord();
        closeWindowThatContains(imageView);
    }

    @FXML
    private void cancelButtonListener() {
        closeWindowThatContains(imageView);
    }
}
