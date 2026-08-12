package com.electricity.controller;

import java.io.IOException;

import com.electricity.dao.BillDAO;
import com.electricity.model.Bill;
import com.electricity.util.BillCalculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calculate")
public class BillServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Guard: only logged-in users may calculate a bill.
        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    "login.jsp?error=Please login first");

            return;
        }

        int userId =
                (int) session.getAttribute("userId");

        String unitsParam =
                request.getParameter("units");

        int units;

        try {
            units = Integer.parseInt(unitsParam.trim());
        } catch (NumberFormatException | NullPointerException e) {

            request.setAttribute(
                    "error",
                    "Please enter a valid whole number of units.");

            request.getRequestDispatcher("dashboard.jsp")
                   .forward(request, response);

            return;
        }

        if (units < 0) {

            request.setAttribute(
                    "error",
                    "Units cannot be negative.");

            request.getRequestDispatcher("dashboard.jsp")
                   .forward(request, response);

            return;
        }

        // Core assignment logic: slab-wise bill calculation.
        Bill bill = BillCalculator.calculate(units);
        bill.setUserId(userId);

        // Persist to MySQL so it appears in the user's history.
        BillDAO billDAO = new BillDAO();
        billDAO.saveBill(bill);

        request.setAttribute("bill", bill);

        request.getRequestDispatcher("dashboard.jsp")
               .forward(request, response);
    }
}
