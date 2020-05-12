package com.katyshevtseva.vocabularyapp.model;

public class Entry {
    private String word;
    private String translation;
    private Integer level;
    private String listName;

    public Entry(String word, String translation, Integer level, String listName) {
        this.word = word;
        this.translation = translation;
        this.level = level;
        this.listName = listName;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public Integer getLevel() {
        return level;
    }

    public String getListName() {
        return listName;
    }
}
