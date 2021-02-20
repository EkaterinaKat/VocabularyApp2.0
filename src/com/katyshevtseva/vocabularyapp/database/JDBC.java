package com.katyshevtseva.vocabularyapp.database;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.model.Entry;
import com.katyshevtseva.vocabularyapp.model.Statistics;
import com.katyshevtseva.vocabularyapp.utils.Utils;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JDBC implements DataBase {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static JDBC instance;
    private Connection connection;
    private Statement statement;

    private JDBC() throws ClassNotFoundException {
        connect();
        if (!catalogueTableExists()) {
            createCatalogueTable();
        }
        if (!entriesTableExists()) {
            createEntriesTable();
        }
        if (!statisticsTableExists()) {
            createStatisticsTable();
        }
        if (!addingStatisticsTableExists()) {
            createAddingStatisticsTable();
        }
    }

    public static JDBC getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new JDBC();
        }
        return instance;
    }

    private void connect() throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Vocabulary_DB.db");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>();
        String query = "SELECT * FROM catalogue";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String listName = resultSet.getString(1);
                catalogue.add(listName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogue;
    }

    private boolean entriesTableExists() {
        return tableExists("entries");
    }

    private boolean catalogueTableExists() {
        return tableExists("catalogue");
    }

    private boolean statisticsTableExists() {
        return tableExists("statistics");
    }

    private boolean addingStatisticsTableExists() {
        return tableExists("addingStatistics");
    }

    private boolean tableExists(String tableToFind) {
        String query = "SELECT name FROM sqlite_master WHERE type = \"table\"";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.equals(tableToFind))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createCatalogueTable() {
        String query = "CREATE TABLE catalogue (\n" +
                "listName STRING\n" +
                ");\n";
        executeUpdate(query);
    }

    private void createEntriesTable() {
        String query = "CREATE TABLE entries (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "word STRING, \n" +
                "translation STRING, \n" +
                "level STRING, \n" +
                "listName STRING, \n" +
                "lastRepeat DATE \n" +
                ");\n";
        executeUpdate(query);
    }

    private void createStatisticsTable() {
        String query = "CREATE TABLE statistics (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "level INTEGER, \n" +
                "allNum INTEGER, \n" +
                "falseNum INTEGER, \n" +
                "dateOfLearning DATE \n" +
                ");\n";
        executeUpdate(query);
    }

    private void createAddingStatisticsTable() {
        String query = "CREATE TABLE addingStatistics (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "dateOfAdding DATE, \n" +
                "numOfAddedEntries INTEGER \n" +
                ");\n";
        executeUpdate(query);
    }

    private void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createList(String listName) {
        String query = String.format("INSERT INTO catalogue (listName)\n" +
                "VALUES(\"%s\")", listName);
        executeUpdate(query);
    }

    @Override
    public void addEntry(String word, String translation, String listName) {
        String query = String.format("INSERT INTO entries (word, translation, level, listName, lastRepeat)\n" +
                        "VALUES (\"%s\", \"%s\", 0, \"%s\", \"%s\")",
                word, translation, listName, dateFormat.format(Utils.getProperDate()));
        executeUpdate(query);
    }

    @Override
    public List<Entry> getEntriesByListName(String listName) {
        List<Entry> listOfEntries = new ArrayList<>();
        String query = String.format("SELECT id, word, translation, level, lastRepeat FROM entries\n" +
                "WHERE listName = \"%s\"", listName);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String word = resultSet.getString(2);
                String translation = resultSet.getString(3);
                int level = resultSet.getInt(4);
                String lastRepeatString = resultSet.getString(5);
                try {
                    listOfEntries.add(new Entry(id, word, translation, level, listName, dateFormat.parse(lastRepeatString)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEntries;
    }

    @Override
    public void deleteList(String listName) {
        saveEntriesToArchiveTable(listName);
        deleteListFromCatalogueAndEntries(listName);
    }

    private void saveEntriesToArchiveTable(String listName) {
        if (!tableExists("archive"))
            createArchiveTable();
        List<Entry> entries = getEntriesByListName(listName);
        for (Entry entry : entries) {
            String query = String.format("INSERT INTO archive (word, translation, level, listName)\n" +
                            "VALUES (\"%s\", \"%s\", \"%d\", \"%s\")", entry.getWord(), entry.getTranslation(),
                    entry.getLevel(), entry.getListName());
            executeUpdate(query);
        }
    }

    private void createArchiveTable() {
        String query = "CREATE TABLE archive (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "word STRING, \n" +
                "translation STRING, \n" +
                "level STRING, \n" +
                "listName STRING \n" +
                ");\n";
        executeUpdate(query);
    }

    private void deleteListFromCatalogueAndEntries(String listName) {
        String query = String.format("DELETE FROM catalogue\n" +
                "WHERE listName=\"%s\"", listName);
        executeUpdate(query);
        query = String.format("DELETE FROM entries\n" +
                "WHERE listName=\"%s\"", listName);
        executeUpdate(query);
    }

    @Override
    public void changeEntryLevel(Entry entry, int newLevel) {
        String query = String.format("UPDATE entries \n" +
                        "\t   SET level = \"%d\", lastRepeat = \"%s\" \n" +
                        "\t   WHERE id = \"%s\" ", newLevel,
                dateFormat.format(Utils.getProperDate()), entry.getId());
        executeUpdate(query);
    }

    @Override
    public void editEntry(Entry entry, String newWord, String newTranslation) {
        String query = String.format("UPDATE entries \n" +
                "\t   SET word = \"%s\", translation = \"%s\" \n" +
                "\t   WHERE id = \"%s\"", newWord, newTranslation, entry.getId());
        executeUpdate(query);
    }

    @Override
    public void deleteEntry(Entry entry) {
        String query = String.format("DELETE FROM entries WHERE id=\"%s\" ", entry.getId());
        executeUpdate(query);
    }

    @Override
    public void renameList(String oldName, String newName) {
        renameListInCatalogue(oldName, newName);
        renameListInEntries(oldName, newName);
    }

    @Override
    public void moveEntry(Entry entry, String list) {
        if (!entry.getListName().equals(list)) {
            deleteEntry(entry);
            addExistingEntryToAnotherList(entry, list);
        }
    }

    @Override
    public void saveStatistics(List<Statistics> statisticsList) {
        for (Statistics statistics : statisticsList) {
            String query = String.format("INSERT INTO statistics (level, allNum, falseNum, dateOfLearning)\n" +
                            "VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",
                    statistics.getLevel(), statistics.getAllNum(), statistics.getFalseNum(), dateFormat.format(statistics.getDate()));
            executeUpdate(query);
        }
    }

    @Override
    public void incrementTodayAddingStatistics() {
        String todayString = dateFormat.format(Utils.getProperDate());
        String query = String.format("SELECT numOfAddedEntries FROM addingStatistics\n" +
                "WHERE dateOfAdding = \"%s\"", todayString);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int numOfAddedEntries = resultSet.getInt(1);
                String query1 = String.format("UPDATE addingStatistics SET numOfAddedEntries = \"%s\" " +
                        "WHERE dateOfAdding = \"%s\" ", numOfAddedEntries + 1, todayString);
                executeUpdate(query1);
            } else {
                String query2 = String.format("INSERT INTO addingStatistics (dateOfAdding, numOfAddedEntries) " +
                        " VALUES (\"%s\", 1)", todayString);
                executeUpdate(query2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addExistingEntryToAnotherList(Entry entry, String list) {
        String query = String.format("INSERT INTO entries (word, translation, level, listName, lastRepeat)\n" +
                        "VALUES (\"%s\", \"%s\", \"%d\", \"%s\", \"%s\")", entry.getWord(), entry.getTranslation(),
                entry.getLevel(), list, dateFormat.format(entry.getLastRepeat()));
        executeUpdate(query);
    }

    private void renameListInCatalogue(String oldName, String newName) {
        String query = String.format("UPDATE catalogue \n" +
                "\t   SET listName = \"%s\" \n" +
                "\t   WHERE listName = \"%s\" ", newName, oldName);
        executeUpdate(query);
    }

    private void renameListInEntries(String oldName, String newName) {
        String query = String.format("UPDATE entries \n" +
                "\t   SET listName = \"%s\" \n" +
                "\t   WHERE listName = \"%s\" ", newName, oldName);
        executeUpdate(query);
    }
}
