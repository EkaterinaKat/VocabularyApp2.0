package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class MessageController {
    private static String message;
    @FXML
    private Label messageLabel;

    public static void showMessage(String messageToShow) {
        message = messageToShow;
        WindowCreator.getInstance().createMessageWindow();
    }

    @FXML
    private void initialize() {
        messageLabel.setText(message);
    }

    @FXML
    private void okButtonListener() {
        closeWindowThatContains(messageLabel);
    }
}
