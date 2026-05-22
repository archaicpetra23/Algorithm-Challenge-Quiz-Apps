package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.manager.QuizManager;
import com.algorithmquiz.manager.TimerManager;
import com.algorithmquiz.model.Player;
import com.algorithmquiz.model.Question;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

/**
 * Controller untuk Quiz Screen.
 * Sesuai PRD FR-03, FR-04, FR-11.
 */
public class QuizController {

    // Header
    @FXML private Label lblQuestionNum;
    @FXML private Label lblLevel;
    @FXML private Label lblScore;
    @FXML private Label lblTimer;
    @FXML private ProgressBar progressBar;

    // Question
    @FXML private Label lblCategory;
    @FXML private Label lblQuestionText;

    // Answer buttons
    @FXML private Button btnA;
    @FXML private Button btnB;
    @FXML private Button btnC;
    @FXML private Button btnD;

    // Feedback
    @FXML private VBox feedbackBox;
    @FXML private Label lblFeedbackTitle;
    @FXML private Label lblFeedbackExplain;

    // Next
    @FXML private Button btnNext;
    @FXML private HBox actionBar;

    private final QuizManager quizManager = new QuizManager();
    private final TimerManager timerManager = new TimerManager();
    private boolean answered = false;

    public void initQuiz(Player player) {
        quizManager.startQuiz(player);
        lblLevel.setText(player.getSelectedLevel().toUpperCase());
        lblLevel.getStyleClass().add("badge-" + player.getSelectedLevel());
        loadCurrentQuestion();
    }

    private void loadCurrentQuestion() {
        answered = false;
        feedbackBox.setVisible(false);
        feedbackBox.setManaged(false);
        actionBar.setVisible(false);
        actionBar.setManaged(false);

        Question q = quizManager.getCurrentQuestion();
        if (q == null) { try { navigateToResult(); } catch (Exception e) { e.printStackTrace(); } return; }

        int idx = quizManager.getCurrentIndex();
        int total = quizManager.getTotalQuestions();

        // Update header
        lblQuestionNum.setText("Soal " + (idx + 1) + " / " + total);
        lblScore.setText("Score: " + quizManager.getCurrentPlayer().getScore());
        progressBar.setProgress((double) idx / total);

        // Update question
        lblCategory.setText("📌 " + formatCategory(q.getCategory()));
        lblQuestionText.setText(q.getQuestionText());

        // Update answer buttons
        setAnswerBtn(btnA, "A", q.getOptionA());
        setAnswerBtn(btnB, "B", q.getOptionB());
        setAnswerBtn(btnC, "C", q.getOptionC());
        setAnswerBtn(btnD, "D", q.getOptionD());
        enableAnswerButtons(true);
        clearAnswerStyles();

        // Animate question in
        FadeTransition ft = new FadeTransition(Duration.millis(300), lblQuestionText);
        ft.setFromValue(0); ft.setToValue(1); ft.play();

        // Start timer
        timerManager.start(q.getTimeLimit(),
            remaining -> {
                lblTimer.setText(remaining + "s");
                if (remaining <= 5) lblTimer.getStyleClass().add("timer-danger");
                else lblTimer.getStyleClass().remove("timer-danger");
            },
            () -> onTimeout(q)
        );
    }

    private void setAnswerBtn(Button btn, String key, String text) {
        btn.setText(key + ".  " + text);
        btn.setUserData(key);
    }

    @FXML private void onAnswerA() { submitAnswer("A"); }
    @FXML private void onAnswerB() { submitAnswer("B"); }
    @FXML private void onAnswerC() { submitAnswer("C"); }
    @FXML private void onAnswerD() { submitAnswer("D"); }

    private void submitAnswer(String answer) {
        if (answered) return;
        answered = true;
        timerManager.stop();
        enableAnswerButtons(false);

        Question q = quizManager.getCurrentQuestion();
        boolean correct = quizManager.submitAnswer(answer, timerManager.getTimeRemaining());

        // Visual feedback on buttons
        highlightButtons(answer, q.getCorrectAnswer());

        // Show feedback panel
        showFeedback(correct, q.getExplanation());

        // Update score
        lblScore.setText("Score: " + quizManager.getCurrentPlayer().getScore());

        actionBar.setVisible(true);
        actionBar.setManaged(true);
        btnNext.setText(quizManager.isFinished() ? "Lihat Hasil →" : "Soal Berikutnya →");
    }

    private void onTimeout(Question q) {
        if (answered) return;
        answered = true;
        quizManager.skipQuestion();
        enableAnswerButtons(false);
        highlightButtons(null, q.getCorrectAnswer());
        showFeedback(false, "⏱ Waktu habis! " + q.getExplanation());
        actionBar.setVisible(true);
        actionBar.setManaged(true);
        btnNext.setText(quizManager.isFinished() ? "Lihat Hasil →" : "Soal Berikutnya →");
    }

    private void showFeedback(boolean correct, String explanation) {
        feedbackBox.getStyleClass().removeAll("feedback-correct", "feedback-wrong");
        feedbackBox.getStyleClass().add(correct ? "feedback-correct" : "feedback-wrong");
        lblFeedbackTitle.setText(correct ? "✅ Jawaban Benar!" : "❌ Jawaban Salah");
        lblFeedbackTitle.getStyleClass().removeAll("text-success", "text-red");
        lblFeedbackTitle.getStyleClass().add(correct ? "text-success" : "text-red");
        lblFeedbackExplain.setText(explanation);
        feedbackBox.setVisible(true);
        feedbackBox.setManaged(true);

        ScaleTransition st = new ScaleTransition(Duration.millis(300), feedbackBox);
        st.setFromX(0.9); st.setFromY(0.9); st.setToX(1.0); st.setToY(1.0); st.play();
    }

    private void highlightButtons(String selected, String correct) {
        for (Button btn : new Button[]{btnA, btnB, btnC, btnD}) {
            String key = (String) btn.getUserData();
            btn.getStyleClass().removeAll("answer-correct", "answer-wrong");
            if (key.equals(correct)) btn.getStyleClass().add("answer-correct");
            else if (selected != null && key.equals(selected)) btn.getStyleClass().add("answer-wrong");
        }
    }

    private void clearAnswerStyles() {
        for (Button btn : new Button[]{btnA, btnB, btnC, btnD}) {
            btn.getStyleClass().removeAll("answer-correct", "answer-wrong");
        }
    }

    private void enableAnswerButtons(boolean enable) {
        btnA.setDisable(!enable);
        btnB.setDisable(!enable);
        btnC.setDisable(!enable);
        btnD.setDisable(!enable);
    }

    @FXML
    private void onNext() throws Exception {
        if (quizManager.isFinished()) {
            navigateToResult();
        } else {
            loadCurrentQuestion();
        }
    }

    private void navigateToResult() throws Exception {
        FXMLLoader loader = App.loadFXML("result");
        ResultController ctrl = loader.getController();
        ctrl.initResult(quizManager.getCurrentPlayer(), quizManager.getScoreManager());
    }

    @FXML
    private void onNavigateResult() throws Exception {
        navigateToResult();
    }

    @FXML
    private void onQuit() throws Exception {
        timerManager.stop();
        App.navigateTo("main-menu");
    }

    private String formatCategory(String cat) {
        if (cat == null) return "Algoritma";
        return switch (cat) {
            case "sorting" -> "Sorting";
            case "graph" -> "Graph Traversal";
            case "structure" -> "Struktur Data";
            case "complexity" -> "Kompleksitas";
            default -> "Algoritma";
        };
    }
}
