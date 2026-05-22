package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.model.Player;
import com.algorithmquiz.repository.LeaderboardRepository;
import com.algorithmquiz.repository.QuestionRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

/**
 * Controller untuk Main Menu.
 */
public class MainMenuController {

    @FXML private Label lblTopScore;
    @FXML private Label lblTotalQuestions;

    private final LeaderboardRepository leaderboardRepo = new LeaderboardRepository();
    private final QuestionRepository questionRepo = new QuestionRepository();

    @FXML
    public void initialize() {
        // Tampilkan statistik
        long total = questionRepo.countAll();
        lblTotalQuestions.setText(String.valueOf(total));

        var top = leaderboardRepo.getTopTen();
        if (!top.isEmpty()) {
            var best = top.get(0);
            lblTopScore.setText(best.getPlayerName() + " - " + best.getScore() + " pts");
        } else {
            lblTopScore.setText("Belum ada pemain");
        }
    }

    @FXML
    private void onStartQuiz() throws Exception {
        App.navigateTo("setup");
    }

    @FXML
    private void onLearnAlgorithm() throws Exception {
        App.navigateTo("learn");
    }

    @FXML
    private void onLeaderboard() throws Exception {
        App.navigateTo("leaderboard");
    }

    @FXML
    private void onExit() {
        App.getPrimaryStage().close();
    }
}
