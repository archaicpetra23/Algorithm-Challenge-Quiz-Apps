package com.algorithmquiz.repository;

import com.algorithmquiz.database.DatabaseManager;
import com.algorithmquiz.model.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Repository untuk operasi CRUD tabel questions.
 * SRP: hanya bertanggung jawab akses data soal.
 */
public class QuestionRepository {

    public void save(Question question) {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(question);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to save question", e);
        }
    }

    public List<Question> findByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Question q WHERE q.level = :level ORDER BY RANDOM()",
                    Question.class
            ).setParameter("level", level).getResultList();
        }
    }

    public List<Question> findByLevelLimited(String level, int limit) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Question q WHERE q.level = :level ORDER BY RANDOM()",
                    Question.class
            ).setParameter("level", level).setMaxResults(limit).getResultList();
        }
    }

    public long countByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT COUNT(q) FROM Question q WHERE q.level = :level",
                    Long.class
            ).setParameter("level", level).uniqueResult();
        }
    }

    public long countAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(q) FROM Question q", Long.class)
                    .uniqueResult();
        }
    }
}
