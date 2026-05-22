package com.algorithmquiz.model;

/**
 * Model untuk menyimpan state pemain selama sesi quiz berlangsung.
 * Implementasi OOP: Encapsulation
 */
public class Player {

    private String name;
    private String selectedLevel;
    private int score;
    private int correctCount;
    private int currentQuestionIndex;

    public Player() {}

    public Player(String name, String selectedLevel) {
        this.name = name;
        this.selectedLevel = selectedLevel;
        this.score = 0;
        this.correctCount = 0;
        this.currentQuestionIndex = 0;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void incrementCorrect() {
        this.correctCount++;
    }

    public void nextQuestion() {
        this.currentQuestionIndex++;
    }

    public void reset() {
        this.score = 0;
        this.correctCount = 0;
        this.currentQuestionIndex = 0;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSelectedLevel() { return selectedLevel; }
    public void setSelectedLevel(String selectedLevel) { this.selectedLevel = selectedLevel; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getCorrectCount() { return correctCount; }
    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
}
