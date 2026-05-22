package com.algorithmquiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity leaderboard.
 * Menyimpan: nama pemain, score, level, tanggal bermain.
 * Sesuai struktur database PRD (tabel leaderboard).
 */
@Entity
@Table(name = "leaderboard")
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String level;

    @Column(name = "play_date")
    private String playDate;

    @Column(name = "correct_count")
    private int correctCount;

    @Column(name = "total_questions")
    private int totalQuestions;

    // Constructors
    public LeaderboardEntry() {}

    public LeaderboardEntry(String playerName, int score, String level,
                            int correctCount, int totalQuestions) {
        this.playerName = playerName;
        this.score = score;
        this.level = level;
        this.correctCount = correctCount;
        this.totalQuestions = totalQuestions;
        this.playDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getPlayDate() { return playDate; }
    public void setPlayDate(String playDate) { this.playDate = playDate; }
    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
}
