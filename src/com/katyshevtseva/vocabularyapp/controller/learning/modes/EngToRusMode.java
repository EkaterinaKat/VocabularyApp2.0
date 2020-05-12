package com.katyshevtseva.vocabularyapp.controller.learning.modes;

import com.katyshevtseva.vocabularyapp.model.Entry;
import javafx.scene.control.Label;

public class EngToRusMode implements LearningMode {
    @Override
    public void setWordLabelText(Label label, Entry entry) {
        label.setText(entry.getWord());
    }

    @Override
    public void setTranslationLabelText(Label label, Entry entry) {
        label.setText(entry.getTranslation());
    }
}
