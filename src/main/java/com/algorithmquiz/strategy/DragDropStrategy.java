package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * Implementasi Strategy untuk soal Drag-and-Drop.
 * Skor berbeda: tidak ada bonus waktu, hanya benar/salah.
 */
public class DragDropStrategy implements QuizStrategy {

    private static final int BASE_SCORE = 15; // Lebih sulit → lebih banyak poin

    @Override
    public boolean processAnswer(Question question, String answer) {
        if (answer == null || question == null) return false;
        return question.getCorrectAnswer().equalsIgnoreCase(answer.trim());
    }

    @Override
    public int calculateScore(Question question, int timeRemaining) {
        return BASE_SCORE;
    }

    @Override
    public String getStrategyName() {
        return "DragDrop";
    }
}
