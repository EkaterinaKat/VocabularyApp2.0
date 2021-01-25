package com.katyshevtseva.vocabularyapp.model;

import java.util.Date;

public class Entry {
    private int id;
    private String word;
    private String translation;
    private String listName;
    private int level;
    private Date lastRepeat;

    public Entry(int id, String word, String translation, int level, String listName, Date lastRepeat) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.level = level;
        this.listName = listName;
        this.lastRepeat = lastRepeat;
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

    public Date getLastRepeat() {
        return lastRepeat;
    }
}
