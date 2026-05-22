package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller untuk Setup Screen (input nama + pilih level).
 * Sesuai PRD FR-01, FR-02.
 */
public class SetupController {

    @FXML private TextField tfPlayerName;
    @FXML private VBox cardEasy;
    @FXML private VBox cardMedium;
    @FXML private VBox cardHard;
    @FXML private Label lblError;

    private String selectedLevel = "easy";

    @FXML
    public void initialize() {
        selectLevel("easy");
    }

    @FXML
    private void onSelectEasy() { selectLevel("easy"); }

    @FXML
    private void onSelectMedium() { selectLevel("medium"); }

    @FXML
    private void onSelectHard() { selectLevel("hard"); }

    private void selectLevel(String level) {
        this.selectedLevel = level;
        cardEasy.getStyleClass().remove("level-card-selected");
        cardMedium.getStyleClass().remove("level-card-selected");
        cardHard.getStyleClass().remove("level-card-selected");
        switch (level) {
            case "easy"   -> cardEasy.getStyleClass().add("level-card-selected");
            case "medium" -> cardMedium.getStyleClass().add("level-card-selected");
            case "hard"   -> cardHard.getStyleClass().add("level-card-selected");
        }
    }

    @FXML
    private void onStartQuiz() throws Exception {
        String name = tfPlayerName.getText().trim();
        if (name.isEmpty()) {
            lblError.setText("⚠ Masukkan nama pemain terlebih dahulu!");
            lblError.setVisible(true);
            return;
        }
        lblError.setVisible(false);

        Player player = new Player(name, selectedLevel);
        FXMLLoader loader = App.loadFXML("quiz");
        QuizController ctrl = loader.getController();
        ctrl.initQuiz(player);
    }

    @FXML
    private void onBack() throws Exception {
        App.navigateTo("main-menu");
    }
}
