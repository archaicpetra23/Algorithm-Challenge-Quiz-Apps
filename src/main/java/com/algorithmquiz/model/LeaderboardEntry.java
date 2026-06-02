package com.algorithmquiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LeaderboardEntry: Kelas entitas (Entity) yang merepresentasikan tabel "leaderboard" dalam database.
 * Menyimpan data riwayat bermain pemain: nama, perolehan skor, tingkat kesulitan kuis, tanggal/jam bermain, serta jumlah jawaban benar.
 * Menerapkan prinsip dasar OOP: Encapsulation (Enkapsulasi).
 */
@Entity
@Table(name = "leaderboard") // Menentukan bahwa objek ini dipetakan ke tabel database bernama "leaderboard"
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key dengan strategi auto-increment
    private Long id;

    @Column(name = "player_name", nullable = false)
    private String playerName;     // Nama pemain

    @Column(nullable = false)
    private int score;             // Total skor akhir kuis

    @Column(nullable = false)
    private String level;             // Kategori level kesulitan kuis ("easy", "medium", "hard")

    @Column(name = "play_date")
    private String playDate;       // Format String tanggal dan waktu sesi kuis dimainkan

    @Column(name = "correct_count")
    private int correctCount;      // Jumlah jawaban benar yang dijawab oleh pemain

    @Column(name = "total_questions")
    private int totalQuestions;    // Jumlah total soal kuis dalam satu sesi (default: 10 soal)

    // Constructor kosong wajib untuk Hibernate ORM
    public LeaderboardEntry() {}

    /**
     * Constructor lengkap untuk membuat entri papan skor baru setelah pemain menyelesaikan kuis.
     * Secara otomatis mengambil tanggal dan waktu saat ini saat objek di-instansiasi.
     */
    public LeaderboardEntry(String playerName, int score, String level,
                            int correctCount, int totalQuestions) {
        this.playerName = playerName;
        this.score = score;
        this.level = level;
        this.correctCount = correctCount;
        this.totalQuestions = totalQuestions;
        // Mengisi tanggal bermain dengan format standar lokal (Contoh: "03 Jun 2026, 01:00")
        this.playDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }

    // ========== GETTERS & SETTERS (Akses Data Terenkapsulasi) ==========
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
