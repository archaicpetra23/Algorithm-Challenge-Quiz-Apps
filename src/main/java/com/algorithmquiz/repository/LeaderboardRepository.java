package com.algorithmquiz.repository;

import com.algorithmquiz.database.DatabaseManager;
import com.algorithmquiz.model.LeaderboardEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * LeaderboardRepository: Kelas repositori yang khusus menangani akses data (CRUD) tabel "leaderboard".
 * Menerapkan Single Responsibility Principle (SRP): Hanya fokus pada interaksi database papan skor.
 */
public class LeaderboardRepository {

    /**
     * Menyimpan data pencapaian skor pemain baru ke tabel leaderboard.
     */
    public void save(LeaderboardEntry entry) {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entry); // Persist data objek ke SQLite
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback(); // Rollback jika ada kegagalan proses insert
            throw new RuntimeException("Failed to save leaderboard entry", e);
        }
    }

    /**
     * Mengambil 10 baris data dengan skor tertinggi secara global (semua level).
     * Diurutkan dari skor terbesar ke terkecil (DESC).
     */
    public List<LeaderboardEntry> getTopTen() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry ORDER BY score DESC",
                    LeaderboardEntry.class
            ).setMaxResults(10).getResultList(); // Batasi hanya mengambil top 10 data
        }
    }

    /**
     * Mengambil 10 baris data dengan skor tertinggi berdasarkan tingkat kesulitan kuis tertentu.
     */
    public List<LeaderboardEntry> getTopTenByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry e WHERE e.level = :level ORDER BY e.score DESC",
                    LeaderboardEntry.class
            ).setParameter("level", level).setMaxResults(10).getResultList();
        }
    }

    /**
     * Mengambil seluruh data riwayat bermain dari database tanpa batasan jumlah baris.
     */
    public List<LeaderboardEntry> getAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry ORDER BY score DESC",
                    LeaderboardEntry.class
            ).getResultList();
        }
    }

    /**
     * Menghapus seluruh baris data di tabel leaderboard untuk mereset riwayat papan skor.
     * Menggunakan HQL Mutation Query yang aman.
     */
    public void deleteAll() {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            // Menjalankan query DELETE untuk semua baris data di tabel
            session.createMutationQuery("DELETE FROM LeaderboardEntry").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        }
    }
}
