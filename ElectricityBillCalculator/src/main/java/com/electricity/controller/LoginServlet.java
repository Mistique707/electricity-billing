package com.electricity.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.electricity.util.DBConnection;
import com.electricity.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        String sql =
                "SELECT id, name, email, password " +
                "FROM users WHERE email = ?";


        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet result =
                    statement.executeQuery();


            if (result.next()) {

                String storedPassword =
                        result.getString("password");


                boolean validPassword =
                        PasswordUtil.checkPassword(
                                password,
                                storedPassword);


                if (validPassword) {

                    HttpSession session =
                            request.getSession();

                    session.setAttribute(
                            "userId",
                            result.getInt("id"));

                    session.setAttribute(
                            "userName",
                            result.getString("name"));

                    session.setAttribute(
                            "userEmail",
                            result.getString("email"));


                    response.sendRedirect(
                            "dashboard.jsp");

                } else {

                    response.sendRedirect(
                            "login.jsp?error=Invalid email or password");
                }

            } else {

                response.sendRedirect(
                        "login.jsp?error=Invalid email or password");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "login.jsp?error=Database error");
        }
    }
}