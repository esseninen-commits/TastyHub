package com.myproject.tastyhub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centralised MySQL connection settings.
 * Override defaults with TASTYHUB_DB_URL, TASTYHUB_DB_USER and
 * TASTYHUB_DB_PASSWORD environment variables, or matching -D system properties.
 */
public final class DBConnection {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/tastyhub_db?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DBConnection() {
        // Utility class.
    }

    private static String setting(String property, String environment, String fallback) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) {
            value = System.getenv(environment);
        }
        return (value == null) ? fallback : value;
    }

    public static Connection getConnection() throws SQLException {
        String url = setting("tastyhub.db.url", "TASTYHUB_DB_URL", DEFAULT_URL);
        String user = setting("tastyhub.db.user", "TASTYHUB_DB_USER", DEFAULT_USER);
        String password = setting("tastyhub.db.password", "TASTYHUB_DB_PASSWORD", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }
}
