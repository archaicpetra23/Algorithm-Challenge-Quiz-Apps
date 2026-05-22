package com.algorithmquiz.manager;

import com.algorithmquiz.model.Player;
import com.algorithmquiz.model.Question;
import com.algorithmquiz.repository.QuestionRepository;
import com.algorithmquiz.strategy.MultipleChoiceStrategy;
import com.algorithmquiz.strategy.QuizStrategy;

import java.util.List;

/**
 * QuizManager: Mengelola state dan alur sesi quiz.
 * SRP: hanya bertanggung jawab mengatur jalannya quiz.
 */
public class QuizManager {

    private static final int QUESTIONS_PER_LEVEL = 10;

    private Player currentPlayer;
    private List<Question> questions;
    private QuizStrategy strategy;
    private final QuestionRepository questionRepo;
    private final ScoreManager scoreManager;

    public QuizManager() {
        this.questionRepo = new QuestionRepository();
        this.scoreManager = new ScoreManager();
        this.strategy = new MultipleChoiceStrategy();
    }

    /**
     * Mulai sesi quiz baru.
     */
    public void startQuiz(Player player) {
        this.currentPlayer = player;
        this.currentPlayer.reset();
        this.questions = questionRepo.findByLevelLimited(player.getSelectedLevel(), QUESTIONS_PER_LEVEL);
        scoreManager.reset();
    }

    /**
     * Proses jawaban pengguna untuk soal saat ini.
     * @return true jika benar
     */
    public boolean submitAnswer(String answer, int timeRemaining) {
        Question q = getCurrentQuestion();
        if (q == null) return false;

        boolean correct = strategy.processAnswer(q, answer);
        if (correct) {
            int points = strategy.calculateScore(q, timeRemaining);
            scoreManager.addScore(points);
            currentPlayer.addScore(points);
            currentPlayer.incrementCorrect();
        }
        currentPlayer.nextQuestion();
        return correct;
    }

    /**
     * Skip soal (timeout).
     */
    public void skipQuestion() {
        currentPlayer.nextQuestion();
    }

    public Question getCurrentQuestion() {
        int idx = currentPlayer.getCurrentQuestionIndex();
        if (questions == null || idx >= questions.size()) return null;
        return questions.get(idx);
    }

    public int getCurrentIndex() {
        return currentPlayer.getCurrentQuestionIndex();
    }

    public int getTotalQuestions() {
        return questions != null ? questions.size() : QUESTIONS_PER_LEVEL;
    }

    public boolean isFinished() {
        return currentPlayer.getCurrentQuestionIndex() >= getTotalQuestions();
    }

    public Player getCurrentPlayer() { return currentPlayer; }
    public ScoreManager getScoreManager() { return scoreManager; }
    public void setStrategy(QuizStrategy strategy) { this.strategy = strategy; }
}
