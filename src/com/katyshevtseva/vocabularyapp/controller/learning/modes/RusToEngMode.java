package com.katyshevtseva.vocabularyapp.controller.learning.modes;

import com.katyshevtseva.vocabularyapp.model.Entry;
import javafx.scene.control.Label;

public class RusToEngMode implements LearningMode {
    @Override
    public void setWordLabelText(Label label, Entry entry) {
        label.setText(entry.getTranslation());
    }

    @Override
    public void setTranslationLabelText(Label label, Entry entry) {
        label.setText(entry.getWord());
    }
}
