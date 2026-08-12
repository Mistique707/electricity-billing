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

- Java 8+ / `javax.servlet` 4.0.1 (Tomcat 8.5/9)
- JSP + Bootstrap 5
- MySQL / MariaDB via **XAMPP** (JDBC through `mysql-connector-j`)
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

## Setup (XAMPP)

> XAMPP provides the **MySQL/MariaDB** backend. Java servlets still need a
> servlet container, so add **Tomcat** — either the official *Tomcat
> add-on for XAMPP* (appears in the XAMPP Control Panel) or a standalone
> Apache Tomcat 8.5 / 9. Tomcat 10+ will **not** work as-is (it uses the
> `jakarta.*` API, this project uses `javax.*`).

### 1. Start XAMPP MySQL

Open the **XAMPP Control Panel** and click **Start** next to **MySQL**.
Defaults are port **3306**, user **root**, **empty** password — which is
exactly what `DBConnection.java` expects. If you changed MySQL's port in
XAMPP, update the port in
`src/main/java/com/electricity/util/DBConnection.java`.

### 2. Create the database

Open **phpMyAdmin** at <http://localhost/phpmyadmin>, go to the
**Import** tab, choose `ElectricityBillCalculator/database/schema.sql`,
and click **Go**. This creates the `electricity_db` database with the
`users` and `bills` tables.

(CLI alternative, from the repo root:)

```bash
C:\xampp\mysql\bin\mysql.exe -u root < ElectricityBillCalculator/database/schema.sql
```

### 3. Build the WAR

```bash
cd ElectricityBillCalculator
mvn clean package
```

This produces `target/ElectricityBillCalculator.war`. No Maven? Open the
`ElectricityBillCalculator` folder as a Maven project in IntelliJ IDEA or
Eclipse and let the IDE build it.

### 4. Deploy to Tomcat

Copy the WAR into Tomcat's `webapps/` folder and start Tomcat:

- **XAMPP Tomcat add-on:** copy the WAR to
  `C:\xampp\tomcat\webapps\`, then click **Start** next to **Tomcat** in
  the XAMPP Control Panel.
- **Standalone Tomcat:** copy the WAR to `<tomcat>\webapps\`, then run
  `bin\startup.bat`.

Then open:

```
http://localhost:8080/ElectricityBillCalculator/
```

Register an account, log in, and calculate a bill.

> **Driver note:** XAMPP ships MariaDB. The bundled MySQL Connector/J
> works with it for this app; if you ever hit a driver-handshake error,
> swap in the MariaDB JDBC driver, or use MySQL Connector/J 8.0.x.

## Usage Flow

1. **Register** a new account (`register.jsp` → `/register`).
2. **Login** (`login.jsp` → `/login`), which creates a session and
   redirects to the dashboard.
3. On the **dashboard**, enter units consumed and submit to `/calculate`.
4. `BillServlet` validates input, computes the slab-wise bill via
   `BillCalculator`, saves it with `BillDAO`, and shows the breakdown plus
   the full bill history.
