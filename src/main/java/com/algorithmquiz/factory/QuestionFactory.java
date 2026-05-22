package com.algorithmquiz.factory;

import com.algorithmquiz.model.Question;

/**
 * Factory Pattern: membuat objek Question berdasarkan level dan type.
 * Memisahkan logika pembuatan objek dari penggunaannya.
 * Sesuai prinsip OCP (Open/Closed Principle).
 */
public class QuestionFactory {

    /**
     * Factory method utama - buat soal berdasarkan parameter.
     */
    public static Question createQuestion(String level, String type, String questionText,
                                          String optA, String optB, String optC, String optD,
                                          String correctAnswer, String explanation,
                                          String category, int timeLimit) {
        return switch (type.toLowerCase()) {
            case "multiple_choice" -> createMultipleChoice(level, questionText, optA, optB, optC, optD,
                    correctAnswer, explanation, category, timeLimit);
            case "dragdrop" -> createDragDrop(level, questionText, optA, optB, optC, optD,
                    correctAnswer, explanation, category, timeLimit);
            case "animation" -> createAnimation(level, questionText, optA, optB, optC, optD,
                    correctAnswer, explanation, category, timeLimit);
            default -> throw new IllegalArgumentException("Unknown question type: " + type);
        };
    }

    /**
     * Buat soal multiple choice standar.
     */
    public static Question createMultipleChoice(String level, String questionText,
                                                 String optA, String optB, String optC, String optD,
                                                 String correctAnswer, String explanation,
                                                 String category, int timeLimit) {
        return new Question(level, "multiple_choice", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Buat soal drag-and-drop.
     */
    public static Question createDragDrop(String level, String questionText,
                                           String optA, String optB, String optC, String optD,
                                           String correctAnswer, String explanation,
                                           String category, int timeLimit) {
        return new Question(level, "dragdrop", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Buat soal berbasis animasi visualisasi.
     */
    public static Question createAnimation(String level, String questionText,
                                            String optA, String optB, String optC, String optD,
                                            String correctAnswer, String explanation,
                                            String category, int timeLimit) {
        return new Question(level, "animation", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Buat soal easy dengan default 30 detik.
     */
    public static Question easyMC(String questionText,
                                   String optA, String optB, String optC, String optD,
                                   String correct, String explanation, String category) {
        return createMultipleChoice("easy", questionText, optA, optB, optC, optD,
                correct, explanation, category, 30);
    }

    /**
     * Buat soal medium dengan default 25 detik.
     */
    public static Question mediumMC(String questionText,
                                     String optA, String optB, String optC, String optD,
                                     String correct, String explanation, String category) {
        return createMultipleChoice("medium", questionText, optA, optB, optC, optD,
                correct, explanation, category, 25);
    }

    /**
     * Buat soal hard dengan default 20 detik.
     */
    public static Question hardMC(String questionText,
                                   String optA, String optB, String optC, String optD,
                                   String correct, String explanation, String category) {
        return createMultipleChoice("hard", questionText, optA, optB, optC, optD,
                correct, explanation, category, 20);
    }
}
