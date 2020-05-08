package com.katyshevtseva.vocabularyapp.model;

public class Entry {
    private String word;
    private String translation;
    private Integer level;
    private String listName;
    private String help;

    public Entry(String word, String translation, Integer level, String listName, String help) {
        this.word = word;
        this.translation = translation;
        this.level = level;
        this.listName = listName;
        this.help = help;
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

    public String getHelp() {
        return help;
    }
}
