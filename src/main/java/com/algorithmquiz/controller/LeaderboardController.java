package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import com.algorithmquiz.model.LeaderboardEntry;
import com.algorithmquiz.repository.LeaderboardRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * Controller untuk Leaderboard Screen.
 * Sesuai PRD FR-05, FR-12.
 */
public class LeaderboardController {

    @FXML private TableView<LeaderboardEntry> tableView;
    @FXML private TableColumn<LeaderboardEntry, Integer> colRank;
    @FXML private TableColumn<LeaderboardEntry, String> colName;
    @FXML private TableColumn<LeaderboardEntry, Integer> colScore;
    @FXML private TableColumn<LeaderboardEntry, String> colLevel;
    @FXML private TableColumn<LeaderboardEntry, String> colDate;
    @FXML private ComboBox<String> cmbFilter;
    @FXML private Label lblEmpty;

    private final LeaderboardRepository repo = new LeaderboardRepository();

    @FXML
    public void initialize() {
        setupTable();
        setupFilter();
        loadData("all");
    }

    private void setupTable() {
        colName.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPlayerName()));
        colScore.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getScore()).asObject());
        colLevel.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getLevel().toUpperCase()));
        colDate.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPlayDate()));
        colRank.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
    }

    private void setupFilter() {
        cmbFilter.getItems().addAll("Semua Level", "Easy", "Medium", "Hard");
        cmbFilter.setValue("Semua Level");
        cmbFilter.setOnAction(e -> {
            String sel = cmbFilter.getValue();
            if ("Semua Level".equals(sel)) loadData("all");
            else loadData(sel.toLowerCase());
        });
    }

    private void loadData(String level) {
        var entries = "all".equals(level)
            ? repo.getTopTen()
            : repo.getTopTenByLevel(level);

        tableView.getItems().setAll(entries);
        lblEmpty.setVisible(entries.isEmpty());
        lblEmpty.setManaged(entries.isEmpty());
    }

    @FXML
    private void onRefresh() {
        String sel = cmbFilter.getValue();
        if ("Semua Level".equals(sel)) loadData("all");
        else loadData(sel.toLowerCase());
    }

    @FXML
    private void onClearAll() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Yakin ingin menghapus semua data leaderboard?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Konfirmasi");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                repo.deleteAll();
                loadData("all");
            }
        });
    }

    @FXML
    private void onBack() throws Exception {
        App.navigateTo("main-menu");
    }
}
