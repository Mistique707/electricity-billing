# Electricity Bill Calculator (Java Servlet + MySQL)

A responsive web application that calculates a monthly electricity bill
using **slab (telescopic) tariff rates**. Built with **Java Servlets +
JSP**, **MySQL** as the backend, and **Bootstrap 5** for a responsive UI.

Users register and log in, enter the number of units consumed, and get a
slab-wise bill breakdown. Every calculation is stored in MySQL and shown
as the user's bill history.

## Tariff Slabs

| Slab | Units            | Rate (Rs. / unit) |
|------|------------------|-------------------|
| 1    | First 50 units   | 3.50              |
| 2    | Next 100 units   | 4.00              |
| 3    | Next 100 units   | 5.20              |
| 4    | Above 250 units  | 6.50              |

The rate is **telescopic**: e.g. for 275 units the first 50 are billed at
3.50, the next 100 at 4.00, the next 100 at 5.20, and the remaining 25 at
6.50 — total **Rs. 1257.50**.

## Features

- User registration & login (passwords hashed with **BCrypt**)
- Session-protected dashboard
- Slab-wise electricity bill calculation
- Bills persisted to MySQL, shown as per-user history
- Responsive layout (mobile / tablet / desktop) via Bootstrap 5

## Tech Stack

- Java 8+ / Jakarta `javax.servlet` 4.0.1 (Tomcat 8.5/9)
- JSP + Bootstrap 5
- MySQL (JDBC via `mysql-connector-j`)
- Maven (WAR packaging)

## Project Structure

```
ElectricityBillCalculator/
├── database/
│   └── schema.sql                     # MySQL database + tables
├── pom.xml
└── src/main/
    ├── java/com/electricity/
    │   ├── controller/
    │   │   ├── LoginServlet.java       # /login
    │   │   ├── LogoutServlet.java      # /logout
    │   │   ├── RegisterServlet.java    # /register
    │   │   └── BillServlet.java        # /calculate  (bill logic)
    │   ├── dao/
    │   │   ├── UserDAO.java
    │   │   └── BillDAO.java            # save + fetch bills
    │   ├── model/
    │   │   └── Bill.java
    │   └── util/
    │       ├── DBConnection.java
    │       ├── PasswordUtil.java
    │       └── BillCalculator.java      # slab-rate calculation
    └── webapp/
        ├── login.jsp
        ├── register.jsp
        ├── dashboard.jsp               # calculator + result + history
        └── WEB-INF/web.xml
```

## Setup

### 1. Database

Ensure MySQL is running on **port 3307** (or edit
`DBConnection.java` to match your setup), then run:

```bash
mysql -u root -P 3307 < ElectricityBillCalculator/database/schema.sql
```

This creates the `electricity_db` database with the `users` and `bills`
tables.

Database credentials live in
`src/main/java/com/electricity/util/DBConnection.java`:

```
URL      = jdbc:mysql://localhost:3307/electricity_db
USER     = root
PASSWORD = (empty)
```

### 2. Build

```bash
cd ElectricityBillCalculator
mvn clean package
```

This produces `target/ElectricityBillCalculator.war`.

### 3. Deploy

Drop the WAR into Tomcat's `webapps/` folder (Tomcat 8.5 or 9, which use
the `javax.servlet` API), start Tomcat, and open:

```
http://localhost:8080/ElectricityBillCalculator/
```

Register an account, log in, and calculate a bill.

## Usage Flow

1. **Register** a new account (`register.jsp` → `/register`).
2. **Login** (`login.jsp` → `/login`), which creates a session and
   redirects to the dashboard.
3. On the **dashboard**, enter units consumed and submit to `/calculate`.
4. `BillServlet` validates input, computes the slab-wise bill via
   `BillCalculator`, saves it with `BillDAO`, and shows the breakdown plus
   the full bill history.
