<%@ page import="com.electricity.model.Bill" %>
<%@ page import="com.electricity.dao.BillDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%

    // ---- Login guard ------------------------------------------------
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp?error=Please login first");
        return;
    }

    int userId = (int) session.getAttribute("userId");
    String userName = (String) session.getAttribute("userName");

    // Result of a calculation (set by BillServlet after a POST).
    Bill bill = (Bill) request.getAttribute("bill");
    String error = (String) request.getAttribute("error");

    // Bill history for this user, straight from MySQL.
    BillDAO billDAO = new BillDAO();
    List<Bill> history = billDAO.getBillsByUser(userId);

    SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMM yyyy, hh:mm a");
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Dashboard - Electricity Bill Calculator</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">

<nav class="navbar navbar-dark bg-primary">

    <div class="container">

        <span class="navbar-brand mb-0 h1">
            &#9889; Electricity Bill Calculator
        </span>

        <div class="d-flex align-items-center">

            <span class="text-white me-3 d-none d-sm-inline">
                Hi, <%= userName %>
            </span>

            <a href="logout"
               class="btn btn-outline-light btn-sm">
                Logout
            </a>

        </div>

    </div>

</nav>


<div class="container my-4">

    <div class="row g-4">

        <!-- ================= Calculator ================= -->
        <div class="col-lg-5">

            <div class="card shadow-sm h-100">

                <div class="card-body p-4">

                    <h4 class="mb-3">
                        Calculate Your Bill
                    </h4>

                    <% if (error != null) { %>

                        <div class="alert alert-danger">
                            <%= error %>
                        </div>

                    <% } %>

                    <form action="calculate"
                          method="post">

                        <div class="mb-3">

                            <label class="form-label">
                                Units Consumed
                            </label>

                            <input
                                type="number"
                                name="units"
                                min="0"
                                step="1"
                                class="form-control"
                                placeholder="e.g. 275"
                                required>

                        </div>

                        <button
                            type="submit"
                            class="btn btn-primary w-100">
                            Calculate
                        </button>

                    </form>

                    <hr class="my-4">

                    <h6 class="text-muted">
                        Tariff Slabs
                    </h6>

                    <table class="table table-sm mb-0">

                        <tbody>
                            <tr>
                                <td>First 50 units</td>
                                <td class="text-end">Rs. 3.50 / unit</td>
                            </tr>
                            <tr>
                                <td>Next 100 units</td>
                                <td class="text-end">Rs. 4.00 / unit</td>
                            </tr>
                            <tr>
                                <td>Next 100 units</td>
                                <td class="text-end">Rs. 5.20 / unit</td>
                            </tr>
                            <tr>
                                <td>Above 250 units</td>
                                <td class="text-end">Rs. 6.50 / unit</td>
                            </tr>
                        </tbody>

                    </table>

                </div>

            </div>

        </div>


        <!-- ================= Result ================= -->
        <div class="col-lg-7">

            <% if (bill != null) { %>

                <div class="card shadow-sm mb-4 border-primary">

                    <div class="card-header bg-primary text-white">
                        Bill Summary &mdash; <%= bill.getUnits() %> units
                    </div>

                    <div class="card-body p-4">

                        <table class="table align-middle">

                            <thead>
                                <tr>
                                    <th>Slab</th>
                                    <th>Rate</th>
                                    <th class="text-end">Amount (Rs.)</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr>
                                    <td>First 50 units</td>
                                    <td>3.50</td>
                                    <td class="text-end">
                                        <%= String.format("%.2f", bill.getSlab1Amount()) %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Next 100 units</td>
                                    <td>4.00</td>
                                    <td class="text-end">
                                        <%= String.format("%.2f", bill.getSlab2Amount()) %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Next 100 units</td>
                                    <td>5.20</td>
                                    <td class="text-end">
                                        <%= String.format("%.2f", bill.getSlab3Amount()) %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Above 250 units</td>
                                    <td>6.50</td>
                                    <td class="text-end">
                                        <%= String.format("%.2f", bill.getSlab4Amount()) %>
                                    </td>
                                </tr>
                            </tbody>

                            <tfoot>
                                <tr class="table-primary fw-bold">
                                    <td colspan="2">Total Payable</td>
                                    <td class="text-end">
                                        Rs. <%= String.format("%.2f", bill.getTotalAmount()) %>
                                    </td>
                                </tr>
                            </tfoot>

                        </table>

                    </div>

                </div>

            <% } %>


            <!-- ================= History ================= -->
            <div class="card shadow-sm">

                <div class="card-header bg-white">
                    Your Bill History
                </div>

                <div class="card-body p-0">

                    <% if (history.isEmpty()) { %>

                        <p class="text-muted text-center p-4 mb-0">
                            No bills calculated yet.
                        </p>

                    <% } else { %>

                        <div class="table-responsive">

                            <table class="table table-striped mb-0">

                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th class="text-end">Units</th>
                                        <th class="text-end">Amount (Rs.)</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    <% for (Bill b : history) { %>

                                        <tr>
                                            <td>
                                                <%= b.getCreatedAt() != null
                                                        ? dateFormat.format(b.getCreatedAt())
                                                        : "-" %>
                                            </td>
                                            <td class="text-end">
                                                <%= b.getUnits() %>
                                            </td>
                                            <td class="text-end">
                                                <%= String.format("%.2f", b.getTotalAmount()) %>
                                            </td>
                                        </tr>

                                    <% } %>

                                </tbody>

                            </table>

                        </div>

                    <% } %>

                </div>

            </div>

        </div>

    </div>

</div>

</body>
</html>
