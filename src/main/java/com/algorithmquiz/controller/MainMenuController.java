package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.model.Player;
import com.algorithmquiz.repository.LeaderboardRepository;
import com.algorithmquiz.repository.QuestionRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

/**
 * MainMenuController: Kelas pengontrol untuk layar menu utama aplikasi (main-menu.fxml).
 * Menyajikan statistik kuis saat ini dan navigasi ke modul Setup, Belajar (Visualizer), Leaderboard, dan Keluar.
 */
public class MainMenuController {

    @FXML private Label lblTopScore;       // Label untuk menampilkan skor tertinggi saat ini
    @FXML private Label lblTotalQuestions; // Label untuk menampilkan jumlah total soal di database

    private final LeaderboardRepository leaderboardRepo = new LeaderboardRepository();
    private final QuestionRepository questionRepo = new QuestionRepository();

    @FXML
    public void initialize() {
        // Tampilkan jumlah total soal kuis yang terdaftar dalam database SQLite
        long total = questionRepo.countAll();
        lblTotalQuestions.setText(String.valueOf(total));

        // Ambil data top skor pertama secara global untuk ditampilkan di menu utama
        var top = leaderboardRepo.getTopTen();
        if (!top.isEmpty()) {
            var best = top.get(0);
            lblTopScore.setText(best.getPlayerName() + " - " + best.getScore() + " pts");
        } else {
            lblTopScore.setText("Belum ada pemain");
        }
    }

    /**
     * Membuka halaman setup profil pemain dan level kesulitan.
     */
    @FXML
    private void onStartQuiz() throws Exception {
        App.navigateTo("setup");
    }

    /**
     * Membuka halaman visualisasi interaktif algoritma (FR-06 - FR-09).
     */
    @FXML
    private void onLearnAlgorithm() throws Exception {
        App.navigateTo("learn");
    }

    /**
     * Membuka halaman papan skor (Leaderboard).
     */
    @FXML
    private void onLeaderboard() throws Exception {
        App.navigateTo("leaderboard");
    }

    /**
     * Menutup jendela aplikasi secara bersih.
     */
    @FXML
    private void onExit() {
        App.getPrimaryStage().close();
    }
}
