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
 * Controller untuk Result Screen.
 * Sesuai PRD FR-10: tampilkan skor, simpan ke leaderboard.
 */
public class ResultController {

    @FXML private Label lblPlayerName;
    @FXML private Label lblScore;
    @FXML private Label lblGrade;
    @FXML private Label lblMessage;
    @FXML private Label lblCorrect;
    @FXML private Label lblWrong;
    @FXML private Label lblLevel;
    @FXML private Label lblSavedStatus;

    private final LeaderboardRepository leaderboardRepo = new LeaderboardRepository();
    private Player player;
    private ScoreManager scoreManager;

    public void initResult(Player player, ScoreManager scoreManager) {
        this.player = player;
        this.scoreManager = scoreManager;

        lblPlayerName.setText("Halo, " + player.getName() + "!");
        lblLevel.setText(player.getSelectedLevel().toUpperCase());
        lblCorrect.setText(String.valueOf(scoreManager.getCorrectAnswers()));
        lblWrong.setText(String.valueOf(10 - scoreManager.getCorrectAnswers()));
        lblGrade.setText(scoreManager.getGrade(10));
        lblMessage.setText(scoreManager.getResultMessage(10));

        // Animasi counter score
        animateScore(0, scoreManager.getTotalScore());

        // Simpan ke leaderboard
        saveToLeaderboard();
    }

    private void animateScore(int from, int to) {
        int[] current = {from};
        int duration = 1200;
        int steps = 30;
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
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(duration + 50), e ->
            lblScore.setText(String.valueOf(to))
        ));
        tl.play();
    }

    private void saveToLeaderboard() {
        try {
            LeaderboardEntry entry = new LeaderboardEntry(
                player.getName(), scoreManager.getTotalScore(),
                player.getSelectedLevel(), scoreManager.getCorrectAnswers(), 10
            );
            leaderboardRepo.save(entry);
            lblSavedStatus.setText("✅ Skor tersimpan ke Leaderboard!");
        } catch (Exception e) {
            lblSavedStatus.setText("⚠ Gagal menyimpan skor.");
        }
    }

    @FXML
    private void onRetry() throws Exception {
        App.navigateTo("setup");
    }

    @FXML
    private void onLeaderboard() throws Exception {
        App.navigateTo("leaderboard");
    }

    @FXML
    private void onMainMenu() throws Exception {
        App.navigateTo("main-menu");
    }
}
