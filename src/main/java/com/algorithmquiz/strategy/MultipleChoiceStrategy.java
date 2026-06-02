package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * MultipleChoiceStrategy: Kelas implementasi dari QuizStrategy khusus untuk soal Pilihan Ganda.
 * Sesuai PRD FR-03: Menangani sistem penilaian otomatis yang menggabungkan skor dasar dan bonus waktu pengerjaan.
 */
public class MultipleChoiceStrategy implements QuizStrategy {

    // Konstanta skor dasar jika berhasil menjawab dengan benar (10 poin)
    private static final int BASE_SCORE = 10;
    
    // Konstanta multiplier pengali bonus waktu (1 poin per detik tersisa)
    private static final int TIME_BONUS_MULTIPLIER = 1;

    /**
     * Mengevaluasi apakah pilihan jawaban yang diklik pemain cocok dengan kunci jawaban asli soal PG.
     */
    @Override
    public boolean processAnswer(Question question, String answer) {
        if (answer == null || question == null) return false;
        // Trim spasi kosong dan lakukan pencocokan tanpa sensitif huruf kapital (case-insensitive)
        return question.getCorrectAnswer().equalsIgnoreCase(answer.trim());
    }

    /**
     * Menghitung total skor untuk jawaban benar: Skor Dasar (10) + Bonus Detik Sisa (Detik * 1).
     */
    @Override
    public int calculateScore(Question question, int timeRemaining) {
        int bonus = Math.max(0, timeRemaining * TIME_BONUS_MULTIPLIER);
        return BASE_SCORE + bonus;
    }

    @Override
    public String getStrategyName() {
        return "Multiple Choice";
    }
}
