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
 * SetupController: Kelas pengontrol untuk layar pengaturan kuis (setup.fxml).
 * Sesuai PRD FR-01 (Input Nama Pemain) dan FR-02 (Pilihan Level Kesulitan: Easy, Medium, Hard).
 * Mengatur transisi perpindahan halaman dan passing data antar controller JavaFX.
 */
public class SetupController {

    @FXML private TextField tfPlayerName; // Input teks nama pemain
    @FXML private VBox cardEasy;          // Card pilihan level Easy
    @FXML private VBox cardMedium;        // Card pilihan level Medium
    @FXML private VBox cardHard;          // Card pilihan level Hard
    @FXML private Label lblError;         // Pesan error jika input kosong

    private String selectedLevel = "easy"; // Level default awal yang dipilih

    @FXML
    public void initialize() {
        selectLevel("easy"); // Set seleksi default ke level Easy saat inisialisasi layout
    }

    // ========== EVENT HANDLER PADA CARD LEVEL ==========
    @FXML
    private void onSelectEasy() { selectLevel("easy"); }

    @FXML
    private void onSelectMedium() { selectLevel("medium"); }

    @FXML
    private void onSelectHard() { selectLevel("hard"); }

    /**
     * Memperbarui visualisasi seleksi card level kesulitan di UI.
     * Mengatur penambahan dan penghapusan class CSS style dinamis.
     */
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

    /**
     * Memulai jalannya kuis: Melakukan validasi nama pemain, membuat objek Player baru,
     * serta menginisialisasi controller halaman kuis (QuizController) dengan data player tersebut.
     */
    @FXML
    private void onStartQuiz() throws Exception {
        String name = tfPlayerName.getText().trim();
        // Validasi input nama tidak boleh kosong
        if (name.isEmpty()) {
            lblError.setText("⚠ Masukkan nama pemain terlebih dahulu!");
            lblError.setVisible(true);
            return;
        }
        lblError.setVisible(false);

        // Buat objek Player baru dengan nama dan level kesulitan terpilih
        Player player = new Player(name, selectedLevel);
        
        // Pindah scene ke layout quiz.fxml
        FXMLLoader loader = App.loadFXML("quiz");
        QuizController ctrl = loader.getController();
        // Lakukan dependency injection passing data Player ke QuizController
        ctrl.initQuiz(player);
    }

    /**
     * Kembali ke halaman utama (Main Menu).
     */
    @FXML
    private void onBack() throws Exception {
        App.navigateTo("main-menu");
    }
}
