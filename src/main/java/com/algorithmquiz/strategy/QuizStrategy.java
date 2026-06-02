package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * QuizStrategy: Interface yang mendefinisikan kontrak untuk Behavioral Design Pattern, yaitu Strategy Pattern.
 * Memungkinkan pemisahan algoritma evaluasi jawaban dan perhitungan skor kuis secara runtime.
 * Sesuai prinsip SOLID: Open/Closed Principle (OCP) dan Dependency Inversion Principle (DIP).
 */
public interface QuizStrategy {

    /**
     * Memproses dan mengevaluasi kebenaran jawaban pengguna terhadap kunci jawaban soal.
     * @param question objek data soal kuis aktif
     * @param answer jawaban yang dipilih oleh pemain ("A", "B", "C", atau "D")
     * @return true jika jawaban sesuai kunci, false jika salah
     */
    boolean processAnswer(Question question, String answer);

    /**
     * Menghitung perolehan poin skor berdasarkan tipe soal dan sisa waktu pengerjaan.
     * @param question objek data soal kuis aktif
     * @param timeRemaining sisa detik waktu pengerjaan soal (digunakan untuk menghitung bonus kecepatan)
     * @return total poin skor yang diperoleh untuk soal tersebut
     */
    int calculateScore(Question question, int timeRemaining);

    /**
     * Mengambil nama strategi yang sedang aktif digunakan untuk keperluan log/analisis.
     * @return Nama strategi dalam bentuk String
     */
    String getStrategyName();
}
