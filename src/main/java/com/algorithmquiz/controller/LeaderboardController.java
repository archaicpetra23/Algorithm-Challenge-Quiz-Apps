package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.model.LeaderboardEntry;
import com.algorithmquiz.repository.LeaderboardRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * LeaderboardController: Kelas pengontrol untuk layar papan skor (leaderboard.fxml).
 * Sesuai PRD FR-05 (Papan Skor Leaderboard) dan FR-12 (Fitur Reset Leaderboard).
 * Menampilkan data tabel dengan urutan peringkat dinamis dan fitur penyaringan (filter) tingkat kesulitan.
 */
public class LeaderboardController {

    @FXML private TableView<LeaderboardEntry> tableView;        // Kontrol tabel JavaFX
    @FXML private TableColumn<LeaderboardEntry, Integer> colRank; // Kolom penomoran peringkat dinamis
    @FXML private TableColumn<LeaderboardEntry, String> colName;  // Kolom nama pemain
    @FXML private TableColumn<LeaderboardEntry, Integer> colScore; // Kolom nilai skor kuis
    @FXML private TableColumn<LeaderboardEntry, String> colLevel; // Kolom tingkat kesulitan
    @FXML private TableColumn<LeaderboardEntry, String> colDate;  // Kolom tanggal pengerjaan kuis
    @FXML private ComboBox<String> cmbFilter;                    // Pilihan penyaringan level kesulitan kuis
    @FXML private Label lblEmpty;                                 // Pesan peringatan jika data kosong

    private final LeaderboardRepository repo = new LeaderboardRepository();

    @FXML
    public void initialize() {
        setupTable();  // Setel properti cell value factory untuk tiap kolom tabel
        setupFilter(); // Setel nilai opsi dan event handler untuk ComboBox filter
        loadData("all"); // Pemuatan data papan peringkat default (semua level)
    }

    /**
     * Mengatur binding data objek LeaderboardEntry ke setiap kolom tabel JavaFX secara dinamis.
     */
    private void setupTable() {
        colName.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPlayerName()));
        colScore.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getScore()).asObject());
        colLevel.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getLevel().toUpperCase()));
        colDate.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPlayDate()));
        
        // Membuat kolom nomor urut peringkat secara dinamis (berdasarkan indeks baris tabel + 1)
        colRank.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
    }

    /**
     * Mengonfigurasi ComboBox filter tingkat kesulitan kuis.
     */
    private void setupFilter() {
        cmbFilter.getItems().addAll("Semua Level", "Easy", "Medium", "Hard");
        cmbFilter.setValue("Semua Level");
        
        // Pasang event action ketika pilihan filter diganti oleh user
        cmbFilter.setOnAction(e -> {
            String sel = cmbFilter.getValue();
            if ("Semua Level".equals(sel)) loadData("all");
            else loadData(sel.toLowerCase());
        });
    }

    /**
     * Memuat data papan peringkat dari database SQLite melalui repositori Hibernate.
     * @param level nama level kuis yang difilter ("all", "easy", "medium", "hard")
     */
    private void loadData(String level) {
        var entries = "all".equals(level)
            ? repo.getTopTen()
            : repo.getTopTenByLevel(level);

        tableView.getItems().setAll(entries);
        
        // Munculkan label pesan kosong jika tidak ada data yang masuk
        lblEmpty.setVisible(entries.isEmpty());
        lblEmpty.setManaged(entries.isEmpty());
    }

    /**
     * Memperbarui / menyegarkan isi data tabel papan peringkat secara manual.
     */
    @FXML
    private void onRefresh() {
        String sel = cmbFilter.getValue();
        if ("Semua Level".equals(sel)) loadData("all");
        else loadData(sel.toLowerCase());
    }

    /**
     * Menghapus seluruh data riwayat papan peringkat di database kuis.
     * Menampilkan kotak dialog modal konfirmasi (Alert Confirmation) terlebih dahulu untuk keamanan.
     */
    @FXML
    private void onClearAll() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Yakin ingin menghapus semua data leaderboard?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Konfirmasi");
        confirm.setHeaderText(null);
        
        // Tunggu respon pengguna pada dialog konfirmasi
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                repo.deleteAll(); // Lakukan hapus data menyeluruh di database
                loadData("all");  // Muat ulang tabel kosong
            }
        });
    }

    /**
     * Kembali ke menu utama aplikasi.
     */
    @FXML
    private void onBack() throws Exception {
        App.navigateTo("main-menu");
    }
}
