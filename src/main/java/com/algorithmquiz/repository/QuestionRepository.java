package com.algorithmquiz.repository;

import com.algorithmquiz.database.DatabaseManager;
import com.algorithmquiz.model.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * QuestionRepository: Kelas repositori yang menangani akses data (CRUD) untuk tabel "questions".
 * Menerapkan Single Responsibility Principle (SRP): Khusus menangani pengambilan dan penyimpanan data soal.
 */
public class QuestionRepository {

    /**
     * Menyimpan data objek Question baru ke database.
     */
    public void save(Question question) {
        Transaction tx = null;
        // Membuka session koneksi database dari DatabaseManager
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            // Memulai transaksi database
            tx = session.beginTransaction();
            session.persist(question); // Menyimpan objek ke database
            tx.commit();               // Commit perubahan transaksi
        } catch (Exception e) {
            // Jika terjadi kesalahan, batalkan transaksi (rollback)
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to save question", e);
        }
    }

    /**
     * Mengambil daftar soal berdasarkan tingkat kesulitan tertentu, diurutkan secara acak.
     */
    public List<Question> findByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Question q WHERE q.level = :level ORDER BY RANDOM()", // Sintaks HQL untuk query acak
                    Question.class
            ).setParameter("level", level).getResultList();
        }
    }

    /**
     * Mengambil daftar soal berdasarkan tingkat kesulitan tertentu dengan batasan jumlah (limit), diurutkan acak.
     * Digunakan untuk mengambil tepat 10 soal secara acak per sesi kuis.
     */
    public List<Question> findByLevelLimited(String level, int limit) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Question q WHERE q.level = :level ORDER BY RANDOM()",
                    Question.class
            ).setParameter("level", level).setMaxResults(limit).getResultList(); // Batasi jumlah baris output
        }
    }

    /**
     * Menghitung total jumlah soal yang ada di database berdasarkan tingkat kesulitan tertentu.
     */
    public long countByLevel(String level) {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT COUNT(q) FROM Question q WHERE q.level = :level",
                    Long.class
            ).setParameter("level", level).uniqueResult();
        }
    }

    /**
     * Menghitung total seluruh soal kuis yang ada di database.
     */
    public long countAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(q) FROM Question q", Long.class)
                    .uniqueResult();
        }
    }
}
