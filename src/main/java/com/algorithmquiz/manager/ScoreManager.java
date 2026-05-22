package com.algorithmquiz.manager;

/**
 * ScoreManager: Mengelola perhitungan skor secara terpisah.
 * SRP: hanya bertanggung jawab kalkulasi dan tracking skor.
 */
public class ScoreManager {

    private int totalScore;
    private int correctAnswers;
    private int wrongAnswers;
    private int skippedAnswers;

    public ScoreManager() {
        reset();
    }

    public void reset() {
        totalScore = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        skippedAnswers = 0;
    }

    public void addScore(int points) {
        totalScore += points;
        correctAnswers++;
    }

    public void recordWrong() {
        wrongAnswers++;
    }

    public void recordSkipped() {
        skippedAnswers++;
    }

    /**
     * Hitung persentase skor.
     */
    public double getPercentage(int totalQuestions) {
        if (totalQuestions == 0) return 0;
        return (correctAnswers / (double) totalQuestions) * 100;
    }

    /**
     * Tentukan grade berdasarkan persentase.
     */
    public String getGrade(int totalQuestions) {
        double pct = getPercentage(totalQuestions);
        if (pct >= 90) return "A";
        if (pct >= 75) return "B";
        if (pct >= 60) return "C";
        if (pct >= 40) return "D";
        return "E";
    }

    /**
     * Tentukan pesan hasil.
     */
    public String getResultMessage(int totalQuestions) {
        double pct = getPercentage(totalQuestions);
        if (pct >= 90) return "Luar Biasa! 🎉";
        if (pct >= 75) return "Bagus Sekali! 👍";
        if (pct >= 60) return "Cukup Baik! 😊";
        if (pct >= 40) return "Perlu Latihan 💪";
        return "Ayo Semangat! 📚";
    }

    // Getters
    public int getTotalScore() { return totalScore; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getWrongAnswers() { return wrongAnswers; }
    public int getSkippedAnswers() { return skippedAnswers; }
}
