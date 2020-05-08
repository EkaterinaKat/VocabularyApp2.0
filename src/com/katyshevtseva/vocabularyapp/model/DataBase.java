package com.katyshevtseva.vocabularyapp.model;

import java.util.List;

public interface DataBase {

    List<String> getCatalogue();

    void createList(String listName);

    void deleteList(String listToDelete);

}
