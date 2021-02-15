package com.katyshevtseva.vocabularyapp.model;

import java.util.Date;

import static com.katyshevtseva.vocabularyapp.utils.Utils.getProperDate;

public class Statistics {
    private int level;
    private int allNum;
    private int falseNum;
    private Date date;

    public Statistics(int level, int allNum, int falseNum, Date date) {
        this.level = level;
        this.allNum = allNum;
        this.falseNum = falseNum;
        this.date = date;
    }

    public Statistics(int level) {
        this.level = level;
        this.allNum = 0;
        this.falseNum = 0;
        this.date = getProperDate();
    }

    public void incrementAllNum() {
        allNum++;
    }

    public void incrementFalseNum() {
        falseNum++;
    }

    public int getAllNum() {
        return allNum;
    }

    public int getFalseNum() {
        return falseNum;
    }

    public Date getDate() {
        return date;
    }

    public int getLevel() {
        return level;
    }
}
