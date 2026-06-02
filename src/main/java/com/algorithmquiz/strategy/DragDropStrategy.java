package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * DragDropStrategy: Kelas implementasi dari QuizStrategy khusus untuk soal tantangan Drag-and-Drop.
 * Memberikan penyesuaian skor karena tingkat kesulitan lebih tinggi (15 poin), namun tidak menerapkan bonus waktu.
 */
public class DragDropStrategy implements QuizStrategy {

    // Konstanta skor dasar untuk tantangan Drag-and-Drop (15 poin)
    private static final int BASE_SCORE = 15; 

    /**
     * Memeriksa kecocokan susunan urutan drop yang dibuat pemain dengan kunci jawaban urutan yang benar.
     */
    @Override
    public boolean processAnswer(Question question, String answer) {
        if (answer == null || question == null) return false;
        return question.getCorrectAnswer().equalsIgnoreCase(answer.trim());
    }

    /**
     * Menghitung skor kuis. Untuk mode Drag-and-Drop, skor yang didapat bersifat tetap/konstan (15 poin) tanpa bonus waktu.
     */
    @Override
    public int calculateScore(Question question, int timeRemaining) {
        return BASE_SCORE;
    }

    @Override
    public String getStrategyName() {
        return "Drag and Drop";
    }
}
