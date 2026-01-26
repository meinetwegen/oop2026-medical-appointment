package edu.aitu.oop3.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static final Properties properties = new Properties();

    private static PostgresDB instance;

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error: config.properties file not found!");
            e.printStackTrace();
        }
    }

    private static final String URL = properties.getProperty("db.url");
    private static final String USER = properties.getProperty("db.user");
    private static final String PASSWORD = properties.getProperty("db.password");


    private PostgresDB() { }


    public static synchronized PostgresDB getInstance() {
        if (instance == null) {
            instance = new PostgresDB();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}