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
 * QuizController: Kelas pengontrol antarmuka layar kuis utama (quiz.fxml).
 * Sesuai PRD FR-03 (kuis interaktif), FR-04 (feedback instan), FR-11 (timer hitung mundur).
 */
public class QuizController {

    // ========== ELEMEN ANTARMUKA (FXML BINDINGS) ==========
    
    // Label Header & Status
    @FXML private Label lblQuestionNum;       // Menampilkan posisi soal (contoh: Soal 1 / 10)
    @FXML private Label lblLevel;             // Menampilkan level kuis (EASY, MEDIUM, HARD)
    @FXML private Label lblScore;             // Menampilkan skor terkini pemain
    @FXML private Label lblTimer;             // Menampilkan teks sisa waktu (detik)
    @FXML private ProgressBar progressBar;    // Bar kemajuan pengerjaan kuis (0.0 s.d 1.0)

    // Bagian Soal
    @FXML private Label lblCategory;          // Menampilkan kategori topik soal (misal: Sorting, Graph, dll.)
    @FXML private Label lblQuestionText;      // Menampilkan teks pertanyaan kuis

    // Tombol Jawaban Pilihan Ganda (A, B, C, D)
    @FXML private Button btnA;
    @FXML private Button btnB;
    @FXML private Button btnC;
    @FXML private Button btnD;

    // Panel Umpan Balik (Feedback Box)
    @FXML private VBox feedbackBox;           // Box panel feedback (ditampilkan setelah menjawab)
    @FXML private Label lblFeedbackTitle;     // Menampilkan status "Jawaban Benar!" atau "Jawaban Salah"
    @FXML private Label lblFeedbackExplain;   // Menampilkan teks pembahasan/penjelasan dari soal terkait

    // Tombol Kontrol Kuis
    @FXML private Button btnNext;             // Tombol melangkah ke soal selanjutnya atau melihat hasil
    @FXML private HBox actionBar;             // Bar bagian bawah wadah tombol kontrol kuis

    // ========== VARIABEL STATE INTERNAL ==========
    
    // Objek pengatur alur kuis kuis
    private final QuizManager quizManager = new QuizManager();
    
    // Objek pengelola waktu kuis
    private final TimerManager timerManager = new TimerManager();
    
    // Menyimpan status apakah pertanyaan saat ini sudah dijawab atau belum
    private boolean answered = false;

    /**
     * Memulai sesi kuis dengan parameter objek Player yang dikirim dari SetupController.
     * @param player objek data pemain aktif
     */
    public void initQuiz(Player player) {
        // Inisialisasi kuis awal pada manager
        quizManager.startQuiz(player);
        
        // Atur teks tingkat kesulitan dan pasang kelas gaya CSS yang sesuai
        lblLevel.setText(player.getSelectedLevel().toUpperCase());
        lblLevel.getStyleClass().add("badge-" + player.getSelectedLevel());
        
        // Muat pertanyaan nomor pertama
        loadCurrentQuestion();
    }

    /**
     * Memuat data pertanyaan kuis yang sedang aktif ke elemen antarmuka pengguna.
     */
    private void loadCurrentQuestion() {
        answered = false; // Reset status menjawab ke false
        
        // Sembunyikan panel feedback dan panel tombol "Selanjutnya" di awal soal baru
        feedbackBox.setVisible(false);
        feedbackBox.setManaged(false);
        actionBar.setVisible(false);
        actionBar.setManaged(false);

        // Ambil objek soal saat ini dari manager kuis
        Question q = quizManager.getCurrentQuestion();
        
        // Jika soal sudah habis (selesai), langsung arahkan ke halaman hasil evaluasi
        if (q == null) { 
            try { 
                navigateToResult(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return; 
        }

        int idx = quizManager.getCurrentIndex();
        int total = quizManager.getTotalQuestions();

        // 1. Perbarui teks info header kuis
        lblQuestionNum.setText("Soal " + (idx + 1) + " / " + total);
        lblScore.setText("Score: " + quizManager.getCurrentPlayer().getScore());
        progressBar.setProgress((double) idx / total); // Atur progress bar

        // 2. Perbarui teks soal dan kategori
        lblCategory.setText("📌 " + formatCategory(q.getCategory()));
        lblQuestionText.setText(q.getQuestionText());

        // 3. Pasang opsi jawaban pada masing-masing tombol pilihan
        setAnswerBtn(btnA, "A", q.getOptionA());
        setAnswerBtn(btnB, "B", q.getOptionB());
        setAnswerBtn(btnC, "C", q.getOptionC());
        setAnswerBtn(btnD, "D", q.getOptionD());
        
        // 4. Aktifkan kembali tombol jawaban dan bersihkan gaya warna dari nomor sebelumnya
        enableAnswerButtons(true);
        clearAnswerStyles();

        // 5. Jalankan animasi Fade-In halus pada teks pertanyaan agar terlihat dinamis
        FadeTransition ft = new FadeTransition(Duration.millis(300), lblQuestionText);
        ft.setFromValue(0); 
        ft.setToValue(1); 
        ft.play();

        // 6. Mulai hitung mundur waktu menggunakan TimerManager
        timerManager.start(q.getTimeLimit(),
            remaining -> {
                // Diupdate setiap detik: Tampilkan sisa waktu di UI
                lblTimer.setText(remaining + "s");
                
                // Jika sisa waktu tinggal 5 detik atau kurang, beri aksen warna merah menyala (kelas CSS)
                if (remaining <= 5) lblTimer.getStyleClass().add("timer-danger");
                else lblTimer.getStyleClass().remove("timer-danger");
            },
            () -> onTimeout(q) // Jika waktu pengerjaan habis, panggil method onTimeout
        );
    }

    /**
     * Memformat tulisan teks dan userdata tombol pilihan jawaban.
     */
    private void setAnswerBtn(Button btn, String key, String text) {
        btn.setText(key + ".  " + text);
        btn.setUserData(key); // Menyimpan huruf pilihan (A/B/C/D) sebagai data identitas tombol
    }

    // ========== CLICK HANDLERS (Aksi Tombol Jawaban) ==========
    @FXML private void onAnswerA() { submitAnswer("A"); }
    @FXML private void onAnswerB() { submitAnswer("B"); }
    @FXML private void onAnswerC() { submitAnswer("C"); }
    @FXML private void onAnswerD() { submitAnswer("D"); }

    /**
     * Mengirim dan memeriksa jawaban yang dipilih oleh pengguna.
     * @param answer huruf jawaban yang ditekan ("A", "B", "C", atau "D")
     */
    private void submitAnswer(String answer) {
        if (answered) return; // Jika sudah dijawab, abaikan klik berikutnya
        answered = true;
        
        timerManager.stop(); // Hentikan hitung mundur timer
        enableAnswerButtons(false); // Nonaktifkan tombol jawaban agar tidak diklik lagi

        Question q = quizManager.getCurrentQuestion();
        
        // Periksa jawaban dan tambahkan skor di dalam QuizManager
        boolean correct = quizManager.submitAnswer(answer, timerManager.getTimeRemaining());

        // Tandai tombol secara visual (hijau jika benar, merah jika salah pilih)
        highlightButtons(answer, q.getCorrectAnswer());

        // Tampilkan panel pop-up penjelasan/pembahasan soal
        showFeedback(correct, q.getExplanation());

        // Perbarui tampilan skor terkini di pojok kanan atas
        lblScore.setText("Score: " + quizManager.getCurrentPlayer().getScore());

        // Tampilkan tombol untuk berpindah nomor soal selanjutnya
        actionBar.setVisible(true);
        actionBar.setManaged(true);
        btnNext.setText(quizManager.isFinished() ? "Lihat Hasil →" : "Soal Berikutnya →");
    }

    /**
     * Penanganan otomatis ketika batas waktu pengerjaan soal habis.
     */
    private void onTimeout(Question q) {
        if (answered) return;
        answered = true;
        
        quizManager.skipQuestion(); // Beri tahu manager untuk melewati soal ini dengan nilai salah
        enableAnswerButtons(false);
        
        // Highlight tombol jawaban yang benar sebagai kisi-kisi jawaban
        highlightButtons(null, q.getCorrectAnswer());
        
        // Tampilkan panel feedback kegagalan waktu habis beserta penjelasannya
        showFeedback(false, "⏱ Waktu habis! " + q.getExplanation());
        
        // Aktifkan tombol next
        actionBar.setVisible(true);
        actionBar.setManaged(true);
        btnNext.setText(quizManager.isFinished() ? "Lihat Hasil →" : "Soal Berikutnya →");
    }

    /**
     * Menampilkan kotak panel feedback di bagian bawah dengan animasi perbesaran skala (zoom in).
     */
    private void showFeedback(boolean correct, String explanation) {
        // Hapus kelas warna kustom feedback sebelumnya
        feedbackBox.getStyleClass().removeAll("feedback-correct", "feedback-wrong");
        feedbackBox.getStyleClass().add(correct ? "feedback-correct" : "feedback-wrong");
        
        // Atur judul teks status
        lblFeedbackTitle.setText(correct ? "✅ Jawaban Benar!" : "❌ Jawaban Salah");
        lblFeedbackTitle.getStyleClass().removeAll("text-success", "text-red");
        lblFeedbackTitle.getStyleClass().add(correct ? "text-success" : "text-red");
        
        // Tampilkan teks penjelasan soal
        lblFeedbackExplain.setText(explanation);
        
        // Tampilkan kontainer box
        feedbackBox.setVisible(true);
        feedbackBox.setManaged(true);

        // Berikan efek animasi muncul pembesaran skala agar premium
        ScaleTransition st = new ScaleTransition(Duration.millis(300), feedbackBox);
        st.setFromX(0.9); st.setFromY(0.9); 
        st.setToX(1.0); st.setToY(1.0); 
        st.play();
    }

    /**
     * Memberikan efek warna khusus (hijau untuk benar, merah untuk salah) pada tombol pilihan jawaban.
     */
    private void highlightButtons(String selected, String correct) {
        for (Button btn : new Button[]{btnA, btnB, btnC, btnD}) {
            String key = (String) btn.getUserData();
            btn.getStyleClass().removeAll("answer-correct", "answer-wrong");
            if (key.equals(correct)) {
                btn.getStyleClass().add("answer-correct"); // Tombol jawaban benar diwarnai hijau
            } else if (selected != null && key.equals(selected)) {
                btn.getStyleClass().add("answer-wrong");   // Tombol pilihan yang salah diwarnai merah
            }
        }
    }

    /**
     * Membersihkan efek kelas CSS penanda benar/salah pada seluruh tombol pilihan.
     */
    private void clearAnswerStyles() {
        for (Button btn : new Button[]{btnA, btnB, btnC, btnD}) {
            btn.getStyleClass().removeAll("answer-correct", "answer-wrong");
        }
    }

    /**
     * Mengaktifkan atau menonaktifkan klik pada tombol-tombol pilihan jawaban.
     */
    private void enableAnswerButtons(boolean enable) {
        btnA.setDisable(!enable);
        btnB.setDisable(!enable);
        btnC.setDisable(!enable);
        btnD.setDisable(!enable);
    }

    /**
     * Aksi ketika tombol "Selanjutnya" ditekan.
     */
    @FXML
    private void onNext() throws Exception {
        if (quizManager.isFinished()) {
            navigateToResult(); // Arahkan ke hasil jika kuis sudah selesai
        } else {
            loadCurrentQuestion(); // Muat pertanyaan berikutnya jika masih ada sisa
        }
    }

    /**
     * Melakukan navigasi layar ke halaman Result (Hasil Kuis).
     */
    private void navigateToResult() throws Exception {
        FXMLLoader loader = App.loadFXML("result");
        ResultController ctrl = loader.getController();
        // Teruskan data pemain aktif dan status perhitungan skor ke ResultController
        ctrl.initResult(quizManager.getCurrentPlayer(), quizManager.getScoreManager());
    }

    @FXML
    private void onNavigateResult() throws Exception {
        navigateToResult();
    }

    /**
     * Aksi tombol keluar (Quit) kuis di pojok atas untuk kembali ke Menu Utama.
     */
    @FXML
    private void onQuit() throws Exception {
        timerManager.stop(); // Hentikan timer yang sedang berjalan
        App.navigateTo("main-menu"); // Kembali ke main menu
    }

    /**
     * Memformat teks nama kategori untuk ditampilkan pada bagian header soal.
     */
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
