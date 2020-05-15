package com.katyshevtseva.vocabularyapp.model;

import com.katyshevtseva.vocabularyapp.controller.MainController;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper {
    private DataBase dataBase = MainController.getDataBase();
    private List<Entry> allEntries = new ArrayList<>();

    public SearchHelper() {
        getAllEntriesFromDB();
    }

    private void getAllEntriesFromDB() {
        List<String> catalogue = dataBase.getCatalogue();
        for (String listName : catalogue) {
            allEntries.addAll(dataBase.getEntriesByListName(listName));
        }
    }

    public List<Entry> searchForEntries(String inputString) {
        List<Entry> result = new ArrayList<>();
        for (Entry entry : allEntries) {
            if (entry.getWord().startsWith(inputString) || entry.getTranslation().startsWith(inputString))
                result.add(entry);
        }
        return result;
    }
}
