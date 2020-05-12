package com.katyshevtseva.vocabularyapp.controller.learning.modes;

import com.katyshevtseva.vocabularyapp.model.Entry;
import javafx.scene.control.Label;

public interface LearningMode {

    void setWordLabelText(Label label, Entry entry);

    void setTranslationLabelText(Label label, Entry entry);
}
