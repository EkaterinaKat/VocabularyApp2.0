package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.WindowCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.vocabularyapp.utils.Constants.IMAGES_PATH;
import static com.katyshevtseva.vocabularyapp.utils.Constants.QUESTION_IMAGE_NAME;
import static com.katyshevtseva.vocabularyapp.utils.Utils.closeWindowThatContains;

public class QuestionController {
    private static AnswerReceiver answerReceiver;
    private static String question;
    @FXML
    public Label label;
    @FXML
    private ImageView imageView;

    static void askQuestion(String questionToAsk, AnswerReceiver receiver) {
        answerReceiver = receiver;
        question = questionToAsk;
        WindowCreator.getInstance().questionWindow();
    }

    @FXML
    private void initialize() {
        Image image = new Image(IMAGES_PATH + QUESTION_IMAGE_NAME);
        imageView.setImage(image);
        label.setText(question);
    }

    @FXML
    private void yesButtonListener() {
        answerReceiver.receivePositiveAnswer();
        closeWindowThatContains(imageView);
    }

    @FXML
    private void noButtonListener() {
        closeWindowThatContains(imageView);
    }
}
