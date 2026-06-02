package com.algorithmquiz.database;

import com.algorithmquiz.model.LeaderboardEntry;
import com.algorithmquiz.model.Question;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * DatabaseManager: Kelas pengelola koneksi database SQLite menggunakan Hibernate ORM.
 * Menerapkan Single Responsibility Principle (SRP): Hanya bertanggung jawab atas konfigurasi dan siklus hidup SessionFactory database.
 */
public class DatabaseManager {

    // Menyimpan satu instance SessionFactory untuk seluruh aplikasi (Singleton-like behavior)
    private static SessionFactory sessionFactory;

    // Private constructor agar kelas ini tidak bisa di-instansiasi secara bebas dari luar
    private DatabaseManager() {}

    /**
     * Metode untuk menginisialisasi konfigurasi Hibernate dengan SQLite.
     * Dipanggil sekali saat aplikasi pertama kali dijalankan (di App.java).
     */
    public static void init() {
        // Jika sessionFactory sudah dibuat sebelumnya, lewati inisialisasi ulang
        if (sessionFactory != null) return;
        try {
            // Membuat objek konfigurasi Hibernate
            Configuration config = new Configuration();

            // 1. Menentukan Driver Database JDBC SQLite yang digunakan
            config.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            
            // 2. Menentukan URL koneksi ke file database lokal (nama file: algorithm_quiz.db)
            config.setProperty("hibernate.connection.url", "jdbc:sqlite:algorithm_quiz.db");
            
            // 3. Menentukan Dialect SQLite agar Hibernate tahu cara menghasilkan sintaks SQL yang sesuai untuk SQLite
            config.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
            
            // 4. hbm2ddl.auto = "update" berarti Hibernate akan membuat atau memperbarui tabel otomatis di database jika ada perubahan struktur kelas model (Entity)
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            
            // 5. Menyembunyikan log sintaks SQL di konsol agar output konsol lebih rapi
            config.setProperty("hibernate.show_sql", "false");
            config.setProperty("hibernate.format_sql", "false");
            
            // 6. Menentukan jumlah koneksi maksimum dalam pool (karena SQLite bersifat file-based, cukup diisi 1 agar menghindari lock file)
            config.setProperty("hibernate.connection.pool_size", "1");

            // Mendaftarkan kelas model (Entity) yang akan dipetakan ke dalam tabel database
            config.addAnnotatedClass(Question.class);          // Kelas model untuk tabel soal kuis
            config.addAnnotatedClass(LeaderboardEntry.class);   // Kelas model untuk tabel papan skor

            // Membangun dan menyimpan SessionFactory berdasarkan konfigurasi di atas
            sessionFactory = config.buildSessionFactory();
            System.out.println("[DB] Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("[DB] Failed to initialize database: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    /**
     * Mengambil instance SessionFactory yang aktif untuk melakukan transaksi database (CRUD).
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("DatabaseManager not initialized. Call init() first.");
        }
        return sessionFactory;
    }

    /**
     * Menutup koneksi database ketika aplikasi dimatikan.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
