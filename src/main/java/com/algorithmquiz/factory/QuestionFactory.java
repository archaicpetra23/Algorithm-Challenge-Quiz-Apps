package com.algorithmquiz.factory;

import com.algorithmquiz.model.Question;

/**
 * QuestionFactory: Kelas yang menerapkan Creational Design Pattern, yaitu Factory Pattern.
 * Memisahkan logika pembuatan objek Question dari kelas pengguna (client) untuk mengurangi ketergantungan (loose coupling).
 * Mendukung prinsip Open/Closed Principle (OCP): Jika ingin menambahkan tipe pertanyaan baru, cukup tambahkan di switch-case factory ini.
 */
public class QuestionFactory {

    /**
     * Metode factory utama untuk meng-instansiasi objek soal baru berdasarkan parameter jenis tipenya.
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
            default -> throw new IllegalArgumentException("Tipe soal tidak dikenali: " + type);
        };
    }

    /**
     * Mempermudah pembuatan objek kuis Pilihan Ganda (Multiple Choice) standar.
     */
    public static Question createMultipleChoice(String level, String questionText,
                                                 String optA, String optB, String optC, String optD,
                                                 String correctAnswer, String explanation,
                                                 String category, int timeLimit) {
        return new Question(level, "multiple_choice", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Mempermudah pembuatan objek kuis tantangan Drag-and-Drop.
     */
    public static Question createDragDrop(String level, String questionText,
                                           String optA, String optB, String optC, String optD,
                                           String correctAnswer, String explanation,
                                           String category, int timeLimit) {
        return new Question(level, "dragdrop", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Mempermudah pembuatan objek kuis yang menampilkan animasi visualisasi di dalam soal.
     */
    public static Question createAnimation(String level, String questionText,
                                            String optA, String optB, String optC, String optD,
                                            String correctAnswer, String explanation,
                                            String category, int timeLimit) {
        return new Question(level, "animation", questionText, optA, optB, optC, optD,
                correctAnswer, explanation, category, timeLimit);
    }

    /**
     * Shorthand untuk membuat soal Easy (Pilihan Ganda) dengan batas waktu default 30 detik.
     */
    public static Question easyMC(String questionText,
                                   String optA, String optB, String optC, String optD,
                                   String correct, String explanation, String category) {
        return createMultipleChoice("easy", questionText, optA, optB, optC, optD,
                correct, explanation, category, 30);
    }

    /**
     * Shorthand untuk membuat soal Medium (Pilihan Ganda) dengan batas waktu default 25 detik.
     */
    public static Question mediumMC(String questionText,
                                     String optA, String optB, String optC, String optD,
                                     String correct, String explanation, String category) {
        return createMultipleChoice("medium", questionText, optA, optB, optC, optD,
                correct, explanation, category, 25);
    }

    /**
     * Shorthand untuk membuat soal Hard (Pilihan Ganda) dengan batas waktu default 20 detik.
     */
    public static Question hardMC(String questionText,
                                   String optA, String optB, String optC, String optD,
                                   String correct, String explanation, String category) {
        return createMultipleChoice("hard", questionText, optA, optB, optC, optD,
                correct, explanation, category, 20);
    }
}
