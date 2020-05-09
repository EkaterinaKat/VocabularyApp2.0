package com.katyshevtseva.vocabularyapp.model;

import java.util.*;

public class LearningTunerHelper {
    private DataBase dataBase;

    public LearningTunerHelper(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /* Возвращает Map, где ключ - название списка, значение - логическое выражение выражющее наличие слов в списке */
    public Map<String, Boolean> getListsToChooseFrom() {
        List<String> catalogue = dataBase.getCatalogue();
        Map<String, Boolean> lists = new HashMap<>();
        for (String listName : catalogue) {
            boolean listContainEntries = !dataBase.getEntriesByListName(listName).isEmpty();
            lists.put(listName, listContainEntries);
        }
        return lists;
    }

    public Set<Integer> getLevelsToChooseFrom(List<String> chosenLists) {
        Set<Integer> levels = new HashSet<>();
        for (String listName : chosenLists) {
            List<Entry> entries = dataBase.getEntriesByListName(listName);
            for (Entry entry : entries) {
                levels.add(entry.getLevel());
            }
        }
        return levels;
    }

    public List<Entry> getEntries(List<String> chosenLists, List<Integer> chosenLevels) {
        List<Entry> entries = new ArrayList<>();
        for (String listName : chosenLists) {
            List<Entry> entriesInList = dataBase.getEntriesByListName(listName);
            for (Entry entry : entriesInList) {
                for (Integer level : chosenLevels) {
                    if (entry.getLevel().equals(level))
                        entries.add(entry);
                }

            }
        }
        return entries;
    }
}
