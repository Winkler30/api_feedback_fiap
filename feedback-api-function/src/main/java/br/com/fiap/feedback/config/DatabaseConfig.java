package br.com.fiap.feedback.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig fromEnv() {
        return new DatabaseConfig(
                getRequiredEnv("DB_URL"),
                getRequiredEnv("DB_USER"),
                getRequiredEnv("DB_PASSWORD"));
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static String getRequiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Variável de ambiente obrigatória não definida: " + name);
        }
        return value.trim();
    }
}
