package com.davidsir1.trabalhon2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = System.getenv("URL");
    private static final String USER = System.getenv("USER");
    private static final String PASS = System.getenv("PASS");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
