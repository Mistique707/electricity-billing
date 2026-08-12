package com.electricity.controller;

import java.io.IOException;

import com.electricity.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        if (name == null ||
            email == null ||
            password == null ||
            name.trim().isEmpty() ||
            email.trim().isEmpty() ||
            password.trim().isEmpty()) {

            response.sendRedirect(
                    "register.jsp?error=Please fill all fields");

            return;
        }


        UserDAO userDAO = new UserDAO();

        boolean registered =
                userDAO.registerUser(
                        name.trim(),
                        email.trim(),
                        password);


        if (registered) {

            response.sendRedirect(
                    "login.jsp?message=Registration successful");

        } else {

            response.sendRedirect(
                    "register.jsp?error=Email already exists");
        }
    }
}