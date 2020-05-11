package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

public class AboutController {
    @FXML
    public ImageView imageView;
    @FXML
    public Label label;

    static void showAbout() {
        WindowCreator.getInstance().createAboutWindow();
    }

    @FXML
    public void initialize() {
        imageView.setImage(new Image(IMAGES_PATH + LOGO_IMAGE_NAME));
        imageView.setFitHeight(BIG_LOGO_SIZE);
        imageView.setFitWidth(BIG_LOGO_SIZE);
        label.setText(ABOUT_TEXT);
    }
}
