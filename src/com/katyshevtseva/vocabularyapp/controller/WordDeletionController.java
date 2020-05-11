package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;
import static com.katyshevtseva.vocabularyapp.utils.Constants.QUESTION_IMAGE_NAME;

public class WordDeletionController {
    private static WordChangeController wordChangeController;
    @FXML
    private ImageView imageView;

    static void deleteWord(WordChangeController controller) {
        wordChangeController = controller;
        WindowCreator.getInstance().createWordDeletionWindow();
    }

    @FXML
    public void initialize() {
        Image image = new Image(IMAGES_PATH + QUESTION_IMAGE_NAME);
        imageView.setImage(image);
    }

    public void deleteButtonListener() {
        wordChangeController.deleteWord();
        Utils.closeWindowThatContains(imageView);
    }

    public void cancelButtonListener() {
        Utils.closeWindowThatContains(imageView);
    }
}
