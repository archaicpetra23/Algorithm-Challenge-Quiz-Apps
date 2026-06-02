package com.algorithmquiz.manager;

import com.algorithmquiz.model.Player;
import com.algorithmquiz.model.Question;
import com.algorithmquiz.repository.QuestionRepository;
import com.algorithmquiz.strategy.MultipleChoiceStrategy;
import com.algorithmquiz.strategy.QuizStrategy;

import java.util.List;

/**
 * QuizManager: Kelas pengelola alur logika utama dan status (state) selama sesi kuis berlangsung.
 * Menerapkan Single Responsibility Principle (SRP): Hanya fokus pada navigasi pertanyaan kuis dan eksekusi strategi penilaian.
 */
public class QuizManager {

    // Konstanta jumlah pertanyaan kuis per sesi pengerjaan (default: 10 soal)
    private static final int QUESTIONS_PER_LEVEL = 10;

    private Player currentPlayer;             // Objek pemain yang sedang aktif bermain kuis
    private List<Question> questions;         // Menyimpan daftar 10 pertanyaan aktif dalam sesi ini
    private QuizStrategy strategy;             // Objek strategi kuis aktif (Strategy Pattern untuk kalkulasi skor)
    private final QuestionRepository questionRepo; // Repositori database untuk menarik data soal
    private final ScoreManager scoreManager;   // Pengelola pencatatan detail skor dan akurasi kuis

    public QuizManager() {
        this.questionRepo = new QuestionRepository();
        this.scoreManager = new ScoreManager();
        // Atur strategi penilaian awal (default) menggunakan Multiple Choice Strategy
        this.strategy = new MultipleChoiceStrategy();
    }

    /**
     * Memulai sesi kuis baru untuk pemain.
     * Mengatur ulang status pemain, mengambil soal acak dari database, dan mereset catatan skor.
     * @param player objek data pemain yang masuk
     */
    public void startQuiz(Player player) {
        this.currentPlayer = player;
        this.currentPlayer.reset(); // Reset indeks soal, jumlah benar, dan skor pemain ke 0
        // Tarik 10 soal acak dari database sesuai tingkat kesulitan yang dipilih pemain
        this.questions = questionRepo.findByLevelLimited(player.getSelectedLevel(), QUESTIONS_PER_LEVEL);
        scoreManager.reset(); // Reset total skor di ScoreManager
    }

    /**
     * Memproses jawaban yang dikirimkan oleh pemain pada soal aktif saat ini.
     * @param answer opsi jawaban yang dipilih ("A", "B", "C", atau "D")
     * @param timeRemaining sisa waktu pengerjaan soal saat ini dalam detik (dipakai untuk bonus waktu)
     * @return true jika jawaban benar, false jika salah
     */
    public boolean submitAnswer(String answer, int timeRemaining) {
        Question q = getCurrentQuestion();
        if (q == null) return false;

        // Mengecek kebenaran jawaban menggunakan strategi kuis aktif (Polimorfisme Strategy Pattern)
        boolean correct = strategy.processAnswer(q, answer);
        if (correct) {
            // Jika benar, kalkulasi skor yang didapat (skor dasar + bonus sisa waktu)
            int points = strategy.calculateScore(q, timeRemaining);
            scoreManager.addScore(points);    // Catat ke ScoreManager
            currentPlayer.addScore(points);   // Tambahkan ke total skor pemain
            currentPlayer.incrementCorrect(); // Tambah jumlah jawaban benar pemain
        }
        
        // Pindahkan posisi penunjuk soal ke nomor berikutnya
        currentPlayer.nextQuestion();
        return correct;
    }

    /**
     * Melewati soal aktif saat ini (dipanggil ketika terjadi batas waktu habis / timeout).
     */
    public void skipQuestion() {
        currentPlayer.nextQuestion(); // Langsung pindah ke indeks soal berikutnya tanpa mengubah skor
    }

    /**
     * Mengambil objek pertanyaan kuis yang sedang dihadapi pemain saat ini.
     */
    public Question getCurrentQuestion() {
        int idx = currentPlayer.getCurrentQuestionIndex();
        if (questions == null || idx >= questions.size()) return null;
        return questions.get(idx);
    }

    /**
     * Mengambil indeks soal aktif saat ini (berbasis 0).
     */
    public int getCurrentIndex() {
        return currentPlayer.getCurrentQuestionIndex();
    }

    /**
     * Mengambil total jumlah soal dalam sesi kuis ini.
     */
    public int getTotalQuestions() {
        return questions != null ? questions.size() : QUESTIONS_PER_LEVEL;
    }

    /**
     * Memeriksa apakah sesi kuis sudah berakhir (seluruh soal selesai dijawab).
     */
    public boolean isFinished() {
        return currentPlayer.getCurrentQuestionIndex() >= getTotalQuestions();
    }

    // ========== GETTERS & SETTERS (Akses Data Terenkapsulasi) ==========
    public Player getCurrentPlayer() { return currentPlayer; }
    
    public ScoreManager getScoreManager() { return scoreManager; }
    
    // Memungkinkan pergantian algoritma evaluasi soal secara runtime (mendukung OCP)
    public void setStrategy(QuizStrategy strategy) { this.strategy = strategy; }
}
