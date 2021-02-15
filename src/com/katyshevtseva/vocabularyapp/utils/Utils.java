package com.katyshevtseva.vocabularyapp.utils;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;

public class Utils {

    private Utils() {
    }

    public static Date getProperDate() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        if (hour > 6)
            return new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
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
