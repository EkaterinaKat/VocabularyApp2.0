package com.katyshevtseva.vocabularyapp.DataBase;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import com.katyshevtseva.vocabularyapp.model.Entry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC implements DataBase {
    private static final JDBC instance = new JDBC();
    private Connection connection;
    private Statement statement;

    private JDBC() {
        connect();
    }

    public static JDBC getInstance() {
        return instance;
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Vocabulary_DB.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
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
        if (!catalogueExists()) {
            createCatalogue();
        }
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

    private boolean catalogueExists() {
        String query = "SELECT name FROM sqlite_master WHERE type = \"table\"";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.equals("catalogue"))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getTableNameByListName(String listName) {
        String tableName = "";
        String sql1 = String.format("SELECT tableName FROM catalogue\n" +
                "WHERE listName = \"%s\"", listName);
        try {
            ResultSet rs = statement.executeQuery(sql1);
            rs.next();
            tableName = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableName;
    }

    private void createCatalogue() {
        String sql = "CREATE TABLE catalogue (\n" +
                "listName STRING,\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "tableName STRING\n" +
                ");\n";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Entry> getEntriesByListName(String listName) {
        String tableName = getTableNameByListName(listName);
        List<Entry> listOfEntries = new ArrayList<>();
        String sql2 = String.format("SELECT word, translation, level FROM %s", tableName);  //todo дублирование
        ResultSet rs;
        try {
            rs = statement.executeQuery(sql2);
            while (rs.next()) {
                String word = rs.getString(1);
                String translation = rs.getString(2);
                Integer level = rs.getInt(3);
                listOfEntries.add(new Entry(word, translation, level, listName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEntries;
    }

    @Override
    public void addEntry(String listName, String word, String translation) {
        String tableName = getTableNameByListName(listName);
        String sql = String.format("INSERT INTO %s (word, translation, level)\n" +
                "VALUES (\"%s\", \"%s\", 0)", tableName, word, translation);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createList(String listName) {
        addListNameToCatalogue(listName);
        int id = getListIdByListName(listName);
        createTableForListWithId(id);
        saveTableNameWithListName(id);
    }

    private void addListNameToCatalogue(String listName) {
        String sql = String.format("INSERT INTO catalogue (listName)\n" +
                "VALUES(\"%s\")", listName);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getListIdByListName(String listName) {
        String sql = String.format("SELECT id FROM catalogue\n" +
                "WHERE listName = \"%s\"", listName);
        ResultSet rs;
        int id = 0;
        try {
            rs = statement.executeQuery(sql);
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void createTableForListWithId(int id) {
        String sql4 = String.format("CREATE TABLE table%d (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "word STRING,\n" +
                "translation STRING,\n" +
                "level INTEGER \n" +
                ");\n", id);
        try {
            statement.executeUpdate(sql4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTableNameWithListName(int id) {
        String sql3 = String.format("UPDATE catalogue \n" +
                "\t   SET tableName = \"table%d\" \n" +
                "\t   WHERE id = \"%d\"", id, id);
        try {
            statement.executeUpdate(sql3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteList(String listName) {
        String tableName = getTableNameByListName(listName);
        String sql1 = String.format("DROP TABLE %s", tableName);
        String sql2 = String.format("DELETE FROM catalogue\n" +
                "WHERE listName=\"%s\"", listName);
        try {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeEntryLevel(Entry entry, int newLevel) {
        String tableName = getTableNameByListName(entry.getListName());
        String sql = String.format("UPDATE %s \n" +
                "\t   SET level = \"%d\" \n" +
                "\t   WHERE word = " +
                "\"%s\" ", tableName, newLevel, entry.getWord());
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Entry> getEntriesForSearch(String inputString) {
        List<Entry> resultList = new ArrayList<>();

        List<String> listNames = getCatalogue();

        for (String listName : listNames) {
            String tableName = getTableNameByListName(listName);
            String sql = String.format("SELECT word, translation, level FROM %s", tableName);  //todo дублирование
            try {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getString(1).startsWith(inputString)) {
                        String word = rs.getString(1);
                        String translation = rs.getString(2);
                        Integer level = rs.getInt(3);
                        resultList.add(new Entry(word, translation, level, listName));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public void editEntry(Entry entry, String newWord, String newTranslation) {
        String tableName = getTableNameByListName(entry.getListName());
        String sql = String.format("UPDATE %s \n" +
                "\t   SET word = \"%s\", translation = \"%s\" \n" +
                "\t   WHERE word = \"%s\"", tableName, newWord, newTranslation, entry.getWord());
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntry(Entry entry) {
        String tableName = getTableNameByListName(entry.getListName());
        String sql = String.format("DELETE FROM %s WHERE word=\"%s\" AND translation = \"%s\" " +
                "AND level = \"%d\"", tableName, entry.getWord(), entry.getTranslation(), entry.getLevel());
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
