package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }

    public void cancelButtonListener() {
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }
}
