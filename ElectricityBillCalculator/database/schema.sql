-- ============================================================
--  Electricity Bill Calculator - MySQL schema
--  Matches DBConnection.java :
--    URL  = jdbc:mysql://localhost:3307/electricity_db
--    USER = root
-- ============================================================

CREATE DATABASE IF NOT EXISTS electricity_db;

USE electricity_db;

-- ---- Users (login / registration) --------------------------
CREATE TABLE IF NOT EXISTS users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL   -- BCrypt hash
);

-- ---- Bills (one row per calculation) -----------------------
CREATE TABLE IF NOT EXISTS bills (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    units      INT NOT NULL,
    amount     DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bills_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);
