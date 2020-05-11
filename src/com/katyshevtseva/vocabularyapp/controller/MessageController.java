package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageController {
    private static String message;
    @FXML
    private Label messageLabel;

    public static void showMessage(String messageToShow) {
        message = messageToShow;
        WindowCreator.getInstance().createMessageWindow();
    }

    @FXML
    public void initialize() {
        messageLabel.setText(message);
    }

    public void okButtonListener() {
        Utils.closeWindowThatContains(messageLabel);
    }
}
