package com.katyshevtseva.vocabularyapp.model;

public class Entry {
    private int id;
    private String word;
    private String translation;
    private String listName;
    private int level;

    public Entry(int id, String word, String translation, int level, String listName) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.level = level;
        this.listName = listName;
    }

    public int getId() {
        return id;
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
