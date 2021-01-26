package com.katyshevtseva.vocabularyapp.model;

import java.util.*;

import static com.katyshevtseva.vocabularyapp.controller.learning.LevelsChoiceController.FINISH_LEVEL;

public class LearningTunerHelper {
    private static final boolean PERIODIC_LEARNING_MODE = true;
    private DataBase dataBase;
    private static Map<Integer, Integer> levelDaysMap = new HashMap<>();

    static {
        levelDaysMap.put(0, 3);
        levelDaysMap.put(1, 4);
        levelDaysMap.put(2, 5);
        levelDaysMap.put(3, 6);
        levelDaysMap.put(4, 8);
        levelDaysMap.put(5, 10);
        levelDaysMap.put(6, 12);
        levelDaysMap.put(7, 15);
    }

    public LearningTunerHelper(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /* Возвращает Map, где ключ - название списка, значение - логическое выражение выражющее наличие слов в списке */
    public Map<String, Boolean> getListsToChooseFrom() {
        List<String> catalogue = dataBase.getCatalogue();
        Map<String, Boolean> lists = new HashMap<>();
        for (String listName : catalogue) {
            lists.put(listName, listContainRipeEntries(dataBase.getEntriesByListName(listName)));
        }
        return lists;
    }

    public Set<Integer> getLevelsToChooseFrom(List<String> chosenLists) {
        Set<Integer> levels = new HashSet<>();
        for (String listName : chosenLists) {
            List<Entry> entries = dataBase.getEntriesByListName(listName);
            for (Entry entry : entries) {
                if (PERIODIC_LEARNING_MODE && entryIsRipe(entry))
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
                    if (entry.getLevel().equals(level) && entryIsRipe(entry))
                        entries.add(entry);
                }

            }
        }
        return entries;
    }

    public static boolean entryIsRipe(Entry entry) {
        if (entry.getLevel() == FINISH_LEVEL)
            return false;
        int daysNeedToPath = levelDaysMap.get(entry.getLevel());
        Date appointmentDay = addDays(entry.getLastRepeat(), daysNeedToPath);
        Date today = new Date();
        boolean equals = appointmentDay.equals(today);
        boolean after = appointmentDay.before(today);
        return equals || after;
    }

    private boolean listContainRipeEntries(List<Entry> entries) {
        for (Entry entry : entries)
            if (entryIsRipe(entry))
                return true;
        return false;
    }

    private static Date addDays(Date inDate, int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        calendar.add(Calendar.DATE, numberOfDays);
        return calendar.getTime();
    }
}
