package com.algorithmquiz.model;

import jakarta.persistence.*;

/**
 * Entity soal quiz.
 * Implementasi OOP: Encapsulation, Inheritance base (diextend SortingQuiz, dll)
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String level; // "easy", "medium", "hard"

    @Column(nullable = false)
    private String type;  // "multiple_choice", "dragdrop", "animation"

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false)
    private String optionA;

    @Column(nullable = false)
    private String optionB;

    @Column(nullable = false)
    private String optionC;

    @Column(nullable = false)
    private String optionD;

    @Column(nullable = false)
    private String correctAnswer; // "A", "B", "C", "D"

    @Column(length = 500)
    private String explanation;

    @Column
    private String category; // "sorting", "graph", "structure", "complexity", "basic"

    @Column
    private int timeLimit = 30; // detik

    // Constructors
    public Question() {}

    public Question(String level, String type, String questionText,
                    String optionA, String optionB, String optionC, String optionD,
                    String correctAnswer, String explanation, String category, int timeLimit) {
        this.level = level;
        this.type = type;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.category = category;
        this.timeLimit = timeLimit;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

    public String getOptionByKey(String key) {
        return switch (key.toUpperCase()) {
            case "A" -> optionA;
            case "B" -> optionB;
            case "C" -> optionC;
            case "D" -> optionD;
            default -> "";
        };
    }
}
