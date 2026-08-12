package com.electricity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.electricity.util.DBConnection;
import com.electricity.util.PasswordUtil;

public class UserDAO {

    public boolean registerUser(
            String name,
            String email,
            String password) {

        String sql =
                "INSERT INTO users (name, email, password) " +
                "VALUES (?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            String hashedPassword =
                    PasswordUtil.hashPassword(password);

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, hashedPassword);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    public ResultSet getUserByEmail(
            Connection connection,
            String email)
            throws SQLException {

        String sql =
                "SELECT * FROM users WHERE email = ?";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setString(1, email);

        return statement.executeQuery();
    }
}