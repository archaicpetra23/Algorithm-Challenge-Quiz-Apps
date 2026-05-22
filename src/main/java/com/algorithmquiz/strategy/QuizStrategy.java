package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * Strategy Pattern Interface untuk mode quiz.
 * Memungkinkan pertukaran algoritma quiz secara runtime.
 * Sesuai OCP dan DIP dalam SOLID.
 */
public interface QuizStrategy {

    /**
     * Proses jawaban pengguna.
     * @param question soal yang sedang dijawab
     * @param answer jawaban pengguna ("A", "B", "C", "D")
     * @return true jika jawaban benar
     */
    boolean processAnswer(Question question, String answer);

    /**
     * Hitung poin yang didapat dari jawaban benar.
     * @param question soal
     * @param timeRemaining sisa waktu (untuk bonus poin)
     */
    int calculateScore(Question question, int timeRemaining);

    /**
     * Nama strategi untuk logging.
     */
    String getStrategyName();
}
