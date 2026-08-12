package com.electricity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.electricity.model.Bill;
import com.electricity.util.DBConnection;

public class BillDAO {

    /**
     * Persists a calculated bill for a user and returns whether the
     * insert succeeded.
     */
    public boolean saveBill(Bill bill) {

        String sql =
                "INSERT INTO bills " +
                "(user_id, units, amount) " +
                "VALUES (?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, bill.getUserId());
            statement.setInt(2, bill.getUnits());
            statement.setDouble(3, bill.getTotalAmount());

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Returns the bill history for a user, newest first.
     */
    public List<Bill> getBillsByUser(int userId) {

        List<Bill> bills = new ArrayList<>();

        String sql =
                "SELECT id, user_id, units, amount, created_at " +
                "FROM bills WHERE user_id = ? " +
                "ORDER BY created_at DESC";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet result =
                         statement.executeQuery()) {

                while (result.next()) {

                    Bill bill = new Bill();

                    bill.setId(result.getInt("id"));
                    bill.setUserId(result.getInt("user_id"));
                    bill.setUnits(result.getInt("units"));
                    bill.setTotalAmount(result.getDouble("amount"));
                    bill.setCreatedAt(result.getTimestamp("created_at"));

                    bills.add(bill);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return bills;
    }
}
