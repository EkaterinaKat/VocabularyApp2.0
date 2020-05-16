package com.katyshevtseva.vocabularyapp.model;

import java.util.List;

public interface DataBase {

    List<String> getCatalogue();

    void createList(String listName);

    void deleteList(String listToDelete);

    List<Entry> getEntriesByListName(String listName);

    void addEntry(String listName, String word, String translation);

    void deleteEntry(Entry entry);

    void editEntry(Entry entry, String word, String translation);

    void changeEntryLevel(Entry entry, int newLevel);

    void renameList(String oldName, String newName);
}
