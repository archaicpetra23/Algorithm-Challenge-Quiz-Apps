package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.manager.ScoreManager;
import com.algorithmquiz.model.LeaderboardEntry;
import com.algorithmquiz.model.Player;
import com.algorithmquiz.repository.LeaderboardRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * ResultController: Kelas pengontrol untuk halaman hasil akhir kuis (result.fxml).
 * Sesuai PRD FR-10: Menampilkan laporan skor, predikat nilai (grade), akurasi pengerjaan,
 * serta menyimpan performa secara otomatis ke database riwayat papan peringkat.
 */
public class ResultController {

    @FXML private Label lblPlayerName;   // Menampilkan nama pemain
    @FXML private Label lblScore;        // Menampilkan skor numerik dengan animasi counter
    @FXML private Label lblGrade;        // Menampilkan huruf predikat kelulusan kuis (A-E)
    @FXML private Label lblMessage;      // Menampilkan pesan motivasi
    @FXML private Label lblCorrect;      // Menampilkan total jawaban benar
    @FXML private Label lblWrong;        // Menampilkan total jawaban salah
    @FXML private Label lblLevel;        // Menampilkan level kesulitan kuis yang dimainkan
    @FXML private Label lblSavedStatus;  // Menampilkan status berhasil/gagal menyimpan data ke database

    private final LeaderboardRepository leaderboardRepo = new LeaderboardRepository();
    private Player player;
    private ScoreManager scoreManager;

    /**
     * Menginisialisasi halaman hasil kuis dengan data pemain dan hasil kalkulasi skor.
     */
    public void initResult(Player player, ScoreManager scoreManager) {
        this.player = player;
        this.scoreManager = scoreManager;

        lblPlayerName.setText("Halo, " + player.getName() + "!");
        lblLevel.setText(player.getSelectedLevel().toUpperCase());
        lblCorrect.setText(String.valueOf(scoreManager.getCorrectAnswers()));
        lblWrong.setText(String.valueOf(10 - scoreManager.getCorrectAnswers()));
        lblGrade.setText(scoreManager.getGrade(10));
        lblMessage.setText(scoreManager.getResultMessage(10));

        // Memulai animasi counter pertambahan skor (efek angka berjalan naik)
        animateScore(0, scoreManager.getTotalScore());

        // Menyimpan rekor nilai baru ke database SQLite
        saveToLeaderboard();
    }

    /**
     * Membuat efek animasi transisi angka skor merayap naik dari 0 ke nilai skor akhir.
     */
    private void animateScore(int from, int to) {
        int[] current = {from};
        int duration = 1200; // Durasi total animasi berjalan (1.2 detik)
        int steps = 30;      // Jumlah langkah pembaruan angka
        int stepDelay = duration / steps;
        double increment = (double)(to - from) / steps;

        Timeline tl = new Timeline();
        for (int i = 1; i <= steps; i++) {
            int step = i;
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(stepDelay * step), e -> {
                current[0] = (int)(from + increment * step);
                lblScore.setText(String.valueOf(current[0]));
            }));
        }
        
        // Memastikan angka akhir terpasang dengan tepat di akhir timeline
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(duration + 50), e ->
            lblScore.setText(String.valueOf(to))
        ));
        tl.play();
    }

    /**
     * Menyimpan data rekor nilai sesi kuis saat ini secara otomatis ke database SQLite via Hibernate.
     */
    private void saveToLeaderboard() {
        try {
            LeaderboardEntry entry = new LeaderboardEntry(
                player.getName(), scoreManager.getTotalScore(),
                player.getSelectedLevel(), scoreManager.getCorrectAnswers(), 10
            );
            leaderboardRepo.save(entry); // Memanggil repository untuk insert data
            lblSavedStatus.setText("✅ Skor tersimpan ke Leaderboard!");
        } catch (Exception e) {
            lblSavedStatus.setText("⚠ Gagal menyimpan skor.");
        }
    }

    /**
     * Mengarahkan kembali ke halaman setup kuis untuk mencoba kuis baru.
     */
    @FXML
    private void onRetry() throws Exception {
        App.navigateTo("setup");
    }

    /**
     * Membuka halaman papan skor (Leaderboard).
     */
    @FXML
    private void onLeaderboard() throws Exception {
        App.navigateTo("leaderboard");
    }

    /**
     * Mengarahkan kembali ke halaman utama (Main Menu).
     */
    @FXML
    private void onMainMenu() throws Exception {
        App.navigateTo("main-menu");
    }
}
