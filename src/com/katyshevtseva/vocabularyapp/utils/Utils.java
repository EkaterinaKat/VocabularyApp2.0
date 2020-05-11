package com.katyshevtseva.vocabularyapp.utils;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;

public class Utils {

    private Utils() {
    }

    public static void setImageOnButton(String imageName, Button button, int imageSize) {
        Image image = new Image(IMAGES_PATH + imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);
        button.graphicProperty().setValue(imageView);
    }

    public static void closeWindowThatContains(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
