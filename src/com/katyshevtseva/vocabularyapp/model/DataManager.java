package com.katyshevtseva.vocabularyapp.model;

import com.katyshevtseva.vocabularyapp.controller.MainController;

import java.util.List;

public class DataManager {

    public static boolean listWithThisNameExists(String listName) {
        List<String> listNames = MainController.getDataBase().getCatalogue();
        return listNames.contains(listName);
    }
}
