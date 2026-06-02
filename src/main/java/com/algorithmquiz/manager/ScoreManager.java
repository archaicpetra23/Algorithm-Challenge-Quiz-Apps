package com.algorithmquiz.manager;

/**
 * ScoreManager: Kelas pengelola perhitungan skor kuis, jumlah jawaban benar, salah, dan dilewati.
 * Menerapkan Single Responsibility Principle (SRP): Hanya bertanggung jawab atas kalkulasi skor dan data performa pemain.
 */
public class ScoreManager {

    private int totalScore;        // Menyimpan total perolehan skor pemain
    private int correctAnswers;    // Jumlah jawaban benar
    private int wrongAnswers;      // Jumlah jawaban salah
    private int skippedAnswers;    // Jumlah soal yang dilewati (timeout)

    public ScoreManager() {
        reset();
    }

    /**
     * Mengatur ulang seluruh data pencatatan skor kembali ke 0.
     */
    public void reset() {
        totalScore = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        skippedAnswers = 0;
    }

    /**
     * Menambahkan poin skor ke total dan menaikkan jumlah jawaban benar.
     * @param points jumlah poin yang diperoleh dari satu soal
     */
    public void addScore(int points) {
        totalScore += points;
        correctAnswers++;
    }

    /**
     * Mencatat satu kejadian jawaban salah.
     */
    public void recordWrong() {
        wrongAnswers++;
    }

    /**
     * Mencatat satu kejadian soal dilewati (karena waktu habis).
     */
    public void recordSkipped() {
        skippedAnswers++;
    }

    /**
     * Menghitung persentase jawaban benar dari total soal kuis.
     * @return nilai persentase (0.0 s.d 100.0)
     */
    public double getPercentage(int totalQuestions) {
        if (totalQuestions == 0) return 0;
        return (correctAnswers / (double) totalQuestions) * 100;
    }

    /**
     * Menentukan predikat Grade huruf (A-E) berdasarkan persentase jawaban benar pemain.
     */
    public String getGrade(int totalQuestions) {
        double pct = getPercentage(totalQuestions);
        if (pct >= 90) return "A"; // Benar >= 90%
        if (pct >= 75) return "B"; // Benar >= 75%
        if (pct >= 60) return "C"; // Benar >= 60%
        if (pct >= 40) return "D"; // Benar >= 40%
        return "E";                // Benar < 40%
    }

    /**
     * Menentukan teks pesan motivasi hasil evaluasi kuis berdasarkan persentase kebenaran.
     */
    public String getResultMessage(int totalQuestions) {
        double pct = getPercentage(totalQuestions);
        if (pct >= 90) return "Luar Biasa! 🎉";
        if (pct >= 75) return "Bagus Sekali! 👍";
        if (pct >= 60) return "Cukup Baik! 😊";
        if (pct >= 40) return "Perlu Latihan 💪";
        return "Ayo Semangat! 📚";
    }

    // ========== GETTERS (Akses Data Terenkapsulasi) ==========
    public int getTotalScore() { return totalScore; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getWrongAnswers() { return wrongAnswers; }
    public int getSkippedAnswers() { return skippedAnswers; }
}
