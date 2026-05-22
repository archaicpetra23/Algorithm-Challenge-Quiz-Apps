package com.algorithmquiz.strategy;

import com.algorithmquiz.model.Question;

/**
 * Implementasi Strategy untuk soal Multiple Choice.
 * Sesuai PRD FR-03: sistem penilaian otomatis + bonus waktu.
 */
public class MultipleChoiceStrategy implements QuizStrategy {

    private static final int BASE_SCORE = 10;
    private static final int TIME_BONUS_MULTIPLIER = 1;

    @Override
    public boolean processAnswer(Question question, String answer) {
        if (answer == null || question == null) return false;
        return question.getCorrectAnswer().equalsIgnoreCase(answer.trim());
    }

    @Override
    public int calculateScore(Question question, int timeRemaining) {
        // Skor dasar + bonus waktu (sisa detik × 1 poin)
        int bonus = Math.max(0, timeRemaining * TIME_BONUS_MULTIPLIER);
        return BASE_SCORE + bonus;
    }

    @Override
    public String getStrategyName() {
        return "MultipleChoice";
    }
}
