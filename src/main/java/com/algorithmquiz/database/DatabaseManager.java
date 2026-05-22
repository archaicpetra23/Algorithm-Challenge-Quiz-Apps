package com.algorithmquiz.database;

import com.algorithmquiz.model.LeaderboardEntry;
import com.algorithmquiz.model.Question;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Mengelola koneksi database SQLite menggunakan Hibernate ORM.
 * SRP: hanya bertanggung jawab konfigurasi & session database.
 */
public class DatabaseManager {

    private static SessionFactory sessionFactory;

    private DatabaseManager() {}

    /**
     * Inisialisasi Hibernate SessionFactory dengan SQLite.
     */
    public static void init() {
        if (sessionFactory != null) return;
        try {
            Configuration config = new Configuration();

            // Hibernate properties
            config.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            config.setProperty("hibernate.connection.url",
                    "jdbc:sqlite:algorithm_quiz.db");
            config.setProperty("hibernate.dialect",
                    "org.hibernate.community.dialect.SQLiteDialect");
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("hibernate.show_sql", "false");
            config.setProperty("hibernate.format_sql", "false");
            config.setProperty("hibernate.connection.pool_size", "1");

            // Register entity classes
            config.addAnnotatedClass(Question.class);
            config.addAnnotatedClass(LeaderboardEntry.class);

            sessionFactory = config.buildSessionFactory();
            System.out.println("[DB] Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("[DB] Failed to initialize database: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("DatabaseManager not initialized. Call init() first.");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
