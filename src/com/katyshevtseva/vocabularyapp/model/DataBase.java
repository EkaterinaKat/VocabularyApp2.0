package com.katyshevtseva.vocabularyapp.model;

import java.util.List;

public interface DataBase {

    List<String> getCatalogue();

    void createList(String listName);

    void deleteList(String listToDelete);

    List<Entry> getEntriesByListName(String listName);

    void addWord(String listName, String word, String translation);

    void deleteWord(Entry entry);

    void editEntry(Entry entry, String word, String translation);

    void editHelp(Entry entry, String help);

}
