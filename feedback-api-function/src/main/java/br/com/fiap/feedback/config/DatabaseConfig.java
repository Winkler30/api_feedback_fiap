package br.com.fiap.feedback.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseConfig {

    private static final HikariDataSource DATA_SOURCE = buildDataSource();
    private static final DatabaseConfig INSTANCE = new DatabaseConfig();

    private DatabaseConfig() {}

    public static DatabaseConfig fromEnv() {
        return INSTANCE;
    }

    public static DataSource dataSource() {
        return DATA_SOURCE;
    }

    public Connection openConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    private static HikariDataSource buildDataSource() {
        String jdbcUrl = mustGetEnv("DB_URL");
        String user = mustGetEnv("DB_USER");
        String password = mustGetEnv("DB_PASSWORD");

        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(user);
        cfg.setPassword(password);

        // Azure Functions: pool pequeno e conservador
        cfg.setMaximumPoolSize(5);
        cfg.setMinimumIdle(0);
        cfg.setConnectionTimeout(10_000);

        return new HikariDataSource(cfg);
    }

    private static String mustGetEnv(String name) {
        String v = System.getenv(name);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalStateException("Variável de ambiente obrigatória não definida: " + name);
        }
        return v.trim();
    }
}
