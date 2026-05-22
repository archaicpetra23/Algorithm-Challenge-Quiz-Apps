package com.algorithmquiz.repository;

import com.algorithmquiz.database.DatabaseManager;
import com.algorithmquiz.model.LeaderboardEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Repository untuk operasi CRUD tabel leaderboard.
 * SRP: hanya bertanggung jawab akses data leaderboard.
 */
public class LeaderboardRepository {

    public void save(LeaderboardEntry entry) {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entry);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to save leaderboard entry", e);
        }
    }

    /**
     * Ambil top 10 skor tertinggi (semua level).
     */
    public List<LeaderboardEntry> getTopTen() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry ORDER BY score DESC",
                    LeaderboardEntry.class
            ).setMaxResults(10).getResultList();
        }
    }

    /**
     * Ambil top 10 berdasarkan level.
     */
    public List<LeaderboardEntry> getTopTenByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry e WHERE e.level = :level ORDER BY e.score DESC",
                    LeaderboardEntry.class
            ).setParameter("level", level).setMaxResults(10).getResultList();
        }
    }

    public List<LeaderboardEntry> getAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM LeaderboardEntry ORDER BY score DESC",
                    LeaderboardEntry.class
            ).getResultList();
        }
    }

    public void deleteAll() {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM LeaderboardEntry").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        }
    }
}
