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

    // Explicitly register the MySQL driver. Under Tomcat, the
    // connector jar lives in the web app's own classloader, and
    // DriverManager's automatic (ServiceLoader) registration does
    // not always kick in there -- which otherwise surfaces as
    // "No suitable driver found". Loading the class forces it to
    // register with DriverManager.
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "MySQL JDBC driver not found on the classpath");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}