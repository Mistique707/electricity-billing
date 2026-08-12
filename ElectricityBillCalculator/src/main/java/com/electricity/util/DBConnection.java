package com.electricity.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // XAMPP's MySQL (MariaDB) defaults: port 3306, user "root",
    // empty password. Change the port here if you moved XAMPP's
    // MySQL off 3306.
    private static final String URL =
            "jdbc:mysql://localhost:3306/electricity_db";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}