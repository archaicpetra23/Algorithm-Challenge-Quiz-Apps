package com.algorithmquiz.controller;

import com.algorithmquiz.App;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.*;

/**
 * LearnController: Kelas pengontrol halaman pembelajaran dan visualisasi interaktif (learn.fxml).
 * Sesuai PRD FR-06 (Sorting), FR-07 (BFS/DFS), FR-08 (Stack/Queue), FR-09 (Drag-and-Drop).
 * Bertanggung jawab mengatur visualisasi algoritma menggunakan JavaFX Canvas dan layout panel.
 */
public class LearnController {

    // Tab buttons
    @FXML private Button tabSorting;
    @FXML private Button tabGraph;
    @FXML private Button tabStructure;
    @FXML private Button tabDragDrop;

    // Sorting
    @FXML private Pane sortingPane;
    @FXML private Canvas sortingCanvas;
    @FXML private ComboBox<String> cmbSortAlgo;
    @FXML private Slider speedSlider;
    @FXML private Label lblSortStep;
    @FXML private Button btnSortPlay;
    @FXML private Button btnSortReset;
    @FXML private Button btnSortStep;

    // Graph
    @FXML private Pane graphPane;
    @FXML private Canvas graphCanvas;
    @FXML private ComboBox<String> cmbGraphAlgo;
    @FXML private Label lblGraphInfo;
    @FXML private Button btnGraphPlay;
    @FXML private Button btnGraphReset;

    // Structure
    @FXML private Pane structurePane;
    @FXML private Canvas structCanvas;
    @FXML private ComboBox<String> cmbStructType;
    @FXML private TextField tfStructInput;
    @FXML private Button btnPush;
    @FXML private Button btnPop;
    @FXML private Label lblStructStatus;

    // Drag Drop
    @FXML private Pane dragPane;
    @FXML private ComboBox<String> cmbDragAlgo;
    @FXML private VBox dragSourceBox;
    @FXML private VBox dragTargetBox;
    @FXML private Label lblDragResult;

    // State variables
    private int[] sortArray = {64, 34, 25, 12, 22, 11, 90};
    private int sortStep = 0;
    private Timeline sortTimeline;
    private List<int[]> sortSteps = new ArrayList<>();

    private Deque<String> structDeque = new ArrayDeque<>();
    private List<String> dragOrder = new ArrayList<>();
    private List<Label> dragItems = new ArrayList<>();

    private int[] graphVisited;
    private Timeline graphTimeline;

    @FXML
    public void initialize() {
        setupSorting();
        setupGraph();
        setupStructure();
        setupDragDrop();
        showTab("sorting");
    }

    // ========== TAB SWITCHING ==========
    @FXML private void onTabSorting()   { showTab("sorting"); }
    @FXML private void onTabGraph()     { showTab("graph"); }
    @FXML private void onTabStructure() { showTab("structure"); }
    @FXML private void onTabDragDrop()  { showTab("dragdrop"); }

    private void showTab(String tab) {
        sortingPane.setVisible("sorting".equals(tab));
        sortingPane.setManaged("sorting".equals(tab));
        graphPane.setVisible("graph".equals(tab));
        graphPane.setManaged("graph".equals(tab));
        structurePane.setVisible("structure".equals(tab));
        structurePane.setManaged("structure".equals(tab));
        dragPane.setVisible("dragdrop".equals(tab));
        dragPane.setManaged("dragdrop".equals(tab));

        for (Button btn : new Button[]{tabSorting, tabGraph, tabStructure, tabDragDrop}) {
            btn.getStyleClass().remove("tab-active");
        }
        switch (tab) {
            case "sorting"   -> tabSorting.getStyleClass().add("tab-active");
            case "graph"     -> tabGraph.getStyleClass().add("tab-active");
            case "structure" -> tabStructure.getStyleClass().add("tab-active");
            case "dragdrop"  -> tabDragDrop.getStyleClass().add("tab-active");
        }

        if ("sorting".equals(tab)) drawSorting(sortArray, -1, -1);
        if ("graph".equals(tab)) drawGraph(new int[]{}, -1);
        if ("structure".equals(tab)) drawStructure();
    }

    // ========== SORTING VISUALIZATION (VISUALISASI PENGURUTAN) ==========
    
    /**
     * Inisialisasi awal untuk modul visualisasi pengurutan.
     */
    private void setupSorting() {
        // Daftarkan algoritma sorting yang didukung ke ComboBox
        cmbSortAlgo.getItems().addAll("Bubble Sort", "Selection Sort", "Insertion Sort");
        cmbSortAlgo.setValue("Bubble Sort");
        // Array awal berantakan yang akan disortir secara visual
        sortArray = new int[]{64, 34, 25, 12, 22, 11, 90};
        // Gambar diagram batang awal
        drawSorting(sortArray, -1, -1);
    }

    /**
     * Memulai animasi visualisasi pengurutan secara otomatis dari awal.
     */
    @FXML private void onSortPlay() {
        // Hentikan animasi berjalan sebelumnya jika ada
        if (sortTimeline != null) sortTimeline.stop();
        
        String algo = cmbSortAlgo.getValue();
        // Generate semua tahapan array di setiap pertukaran (swap) / iterasi
        sortSteps = generateSortSteps(algo, sortArray.clone());
        sortStep = 0; // Reset langkah ke-0
        
        // Membaca input slider kecepatan (makin tinggi nilai slider, jeda detik makin kecil/cepat)
        double speed = speedSlider != null ? Math.max(0.05, 2.0 / speedSlider.getValue()) : 0.8;
        
        // Membuat Timeline JavaFX untuk merender tahapan array satu demi satu
        sortTimeline = new Timeline(new KeyFrame(Duration.seconds(speed), e -> {
            // Jika sudah mencapai akhir langkah, hentikan animasi
            if (sortStep >= sortSteps.size()) { 
                sortTimeline.stop(); 
                lblSortStep.setText("Selesai!"); 
                return; 
            }
            
            // Ambil array state pada langkah saat ini
            int[] state = sortSteps.get(sortStep);
            // Elemen indeks hi1 dan hi2 adalah elemen yang sedang dibandingkan (diwarnai merah)
            int hi1 = state[state.length - 2];
            int hi2 = state[state.length - 1];
            
            // Gambar ulang elemen array pada canvas
            drawSorting(Arrays.copyOf(state, state.length - 2), hi1, hi2);
            // Perbarui label langkah saat ini
            lblSortStep.setText("Step " + (sortStep + 1) + " / " + sortSteps.size());
            sortStep++;
        }));
        
        // Atur perulangan sebanyak total langkah pengurutan
        sortTimeline.setCycleCount(sortSteps.size() + 1);
        // Mulai jalankan animasi
        sortTimeline.play();
    }

    @FXML private void onSortReset() {
        if (sortTimeline != null) sortTimeline.stop();
        sortArray = new int[]{64, 34, 25, 12, 22, 11, 90};
        sortStep = 0;
        lblSortStep.setText("Tekan Play untuk mulai");
        drawSorting(sortArray, -1, -1);
    }

    @FXML private void onSortStep() {
        if (sortSteps.isEmpty()) {
            sortSteps = generateSortSteps(cmbSortAlgo.getValue(), sortArray.clone());
        }
        if (sortStep < sortSteps.size()) {
            int[] state = sortSteps.get(sortStep);
            int hi1 = state[state.length - 2];
            int hi2 = state[state.length - 1];
            drawSorting(Arrays.copyOf(state, state.length - 2), hi1, hi2);
            lblSortStep.setText("Step " + (sortStep + 1) + " / " + sortSteps.size());
            sortStep++;
        }
    }

    private void drawSorting(int[] arr, int hi1, int hi2) {
        GraphicsContext gc = sortingCanvas.getGraphicsContext2D();
        double w = sortingCanvas.getWidth();
        double h = sortingCanvas.getHeight();
        gc.clearRect(0, 0, w, h);

        int n = arr.length;
        double barW = (w - 40) / n;
        int maxVal = Arrays.stream(arr).max().orElse(100);

        for (int i = 0; i < n; i++) {
            double barH = ((double) arr[i] / maxVal) * (h - 60);
            double x = 20 + i * barW;
            double y = h - barH - 30;

            if (i == hi1 || i == hi2) {
                gc.setFill(Color.web("#C62828"));
            } else {
                gc.setFill(Color.web("#3D3535"));
            }
            gc.fillRoundRect(x + 4, y, barW - 8, barH, 6, 6);

            gc.setFill(Color.web("#000000"));
            gc.setFont(Font.font("Inter", FontWeight.BOLD, 13));
            gc.fillText(String.valueOf(arr[i]), x + barW / 2 - 8, h - 10);
        }
    }

    private List<int[]> generateSortSteps(String algo, int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int n = arr.length;
        switch (algo) {
            case "Bubble Sort" -> {
                for (int i = 0; i < n - 1; i++)
                    for (int j = 0; j < n - i - 1; j++) {
                        steps.add(makeStep(arr, j, j + 1));
                        if (arr[j] > arr[j + 1]) { int t = arr[j]; arr[j] = arr[j+1]; arr[j+1] = t; }
                        steps.add(makeStep(arr, j, j + 1));
                    }
            }
            case "Selection Sort" -> {
                for (int i = 0; i < n - 1; i++) {
                    int min = i;
                    for (int j = i + 1; j < n; j++) {
                        steps.add(makeStep(arr, min, j));
                        if (arr[j] < arr[min]) min = j;
                    }
                    int t = arr[min]; arr[min] = arr[i]; arr[i] = t;
                    steps.add(makeStep(arr, i, min));
                }
            }
            case "Insertion Sort" -> {
                for (int i = 1; i < n; i++) {
                    int key = arr[i], j = i - 1;
                    steps.add(makeStep(arr, i, j));
                    while (j >= 0 && arr[j] > key) {
                        arr[j + 1] = arr[j]; j--;
                        steps.add(makeStep(arr, i, j + 1));
                    }
                    arr[j + 1] = key;
                    steps.add(makeStep(arr, j + 1, i));
                }
            }
        }
        return steps;
    }

    private int[] makeStep(int[] arr, int hi1, int hi2) {
        int[] step = new int[arr.length + 2];
        System.arraycopy(arr, 0, step, 0, arr.length);
        step[arr.length] = hi1;
        step[arr.length + 1] = hi2;
        return step;
    }

    // ========== GRAPH VISUALIZATION ==========
    // Graph: 6 nodes — positions fit within canvas 860x260
    private final String[] NODE_LABELS = {"A","B","C","D","E","F"};
    private final int[][] EDGES = {{0,1},{0,2},{1,3},{1,4},{2,4},{4,5}};
    // Repositioned so all nodes fit within canvas height ~260px
    private final double[][] NODE_POS = {{300,45},{150,135},{460,135},{75,225},{310,225},{530,225}};

    /**
     * Inisialisasi awal untuk modul penelusuran graf.
     */
    private void setupGraph() {
        if (cmbGraphAlgo != null) {
            cmbGraphAlgo.getItems().addAll("BFS", "DFS");
            cmbGraphAlgo.setValue("BFS");
        }
    }

    /**
     * Memulai animasi penelusuran graf (BFS/DFS) langkah demi langkah.
     */
    @FXML private void onGraphPlay() {
        if (graphTimeline != null) graphTimeline.stop();
        String algo = cmbGraphAlgo != null ? cmbGraphAlgo.getValue() : "BFS";
        
        // Dapatkan urutan kunjungan node sesuai algoritma pilihan (BFS/DFS)
        List<Integer> order = "BFS".equals(algo) ? bfsOrder() : dfsOrder();
        int[] step = {0};
        
        // Animasikan proses kunjungan node setiap 0.8 detik sekali
        graphTimeline = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> {
            if (step[0] >= order.size()) {
                graphTimeline.stop();
                if (lblGraphInfo != null) lblGraphInfo.setText(algo + " selesai! Semua node dikunjungi.");
                return;
            }
            
            // Siapkan array dari seluruh node yang telah berhasil dikunjungi sampai step ini
            int[] visited = new int[step[0] + 1];
            for (int i = 0; i <= step[0]; i++) visited[i] = order.get(i);
            
            // Gambar ulang graf di canvas (node aktif berwarna merah, visited berwarna abu-abu gelap)
            drawGraph(visited, order.get(step[0]));
            if (lblGraphInfo != null)
                lblGraphInfo.setText(algo + " -> Mengunjungi: " + NODE_LABELS[order.get(step[0])]);
            step[0]++;
        }));
        
        graphTimeline.setCycleCount(order.size() + 1);
        graphTimeline.play();
    }

    /**
     * Mengembalikan tampilan graf ke keadaan awal (belum ada node yang dikunjungi).
     */
    @FXML private void onGraphReset() {
        if (graphTimeline != null) graphTimeline.stop();
        drawGraph(new int[]{}, -1);
        if (lblGraphInfo != null) lblGraphInfo.setText("Tekan Play untuk mulai traversal");
    }

    private List<Integer> bfsOrder() {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[6];
        Queue<Integer> q = new LinkedList<>();
        q.add(0); vis[0] = true;
        while (!q.isEmpty()) {
            int node = q.poll();
            order.add(node);
            for (int[] e : EDGES) {
                if (e[0] == node && !vis[e[1]]) { vis[e[1]] = true; q.add(e[1]); }
                if (e[1] == node && !vis[e[0]]) { vis[e[0]] = true; q.add(e[0]); }
            }
        }
        return order;
    }

    private List<Integer> dfsOrder() {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[6];
        dfsHelper(0, vis, order);
        return order;
    }

    private void dfsHelper(int node, boolean[] vis, List<Integer> order) {
        vis[node] = true;
        order.add(node);
        for (int[] e : EDGES) {
            if (e[0] == node && !vis[e[1]]) dfsHelper(e[1], vis, order);
            if (e[1] == node && !vis[e[0]]) dfsHelper(e[0], vis, order);
        }
    }

    private void drawGraph(int[] visited, int current) {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        Set<Integer> visitedSet = new HashSet<>();
        for (int v : visited) visitedSet.add(v);

        // Draw edges
        gc.setStroke(Color.web("#E8E0E0")); gc.setLineWidth(2);
        for (int[] e : EDGES) {
            gc.strokeLine(NODE_POS[e[0]][0], NODE_POS[e[0]][1], NODE_POS[e[1]][0], NODE_POS[e[1]][1]);
        }

        // Draw nodes
        for (int i = 0; i < 6; i++) {
            double x = NODE_POS[i][0], y = NODE_POS[i][1];
            if (i == current) gc.setFill(Color.web("#C62828"));
            else if (visitedSet.contains(i)) gc.setFill(Color.web("#3D3535"));
            else gc.setFill(Color.web("#EDE5E5"));

            gc.fillOval(x - 22, y - 22, 44, 44);
            gc.setFill(visitedSet.contains(i) ? Color.WHITE : Color.web("#3D3535"));
            gc.setFont(Font.font("Inter", FontWeight.BOLD, 15));
            gc.fillText(NODE_LABELS[i], x - 5, y + 5);
        }
    }

    // ========== STACK & QUEUE VISUALIZATION ==========
    private void setupStructure() {
        if (cmbStructType != null) {
            cmbStructType.getItems().addAll("Stack", "Queue");
            cmbStructType.setValue("Stack");
        }
    }

    /** Quick-value presets untuk Stack/Queue */
    @FXML private void onPreset1()   { quickPush("1"); }
    @FXML private void onPreset5()   { quickPush("5"); }
    @FXML private void onPreset10()  { quickPush("10"); }
    @FXML private void onPreset25()  { quickPush("25"); }
    @FXML private void onPreset50()  { quickPush("50"); }
    @FXML private void onPreset100() { quickPush("100"); }

    private void quickPush(String val) {
        if (tfStructInput != null) tfStructInput.setText(val);
        onStructPush();
    }

    /**
     * Memasukkan data baru (Push untuk Stack atau Enqueue untuk Queue).
     */
    @FXML private void onStructPush() {
        String val = tfStructInput != null ? tfStructInput.getText().trim() : "X";
        if (val.isEmpty()) val = "?";
        String type = cmbStructType != null ? cmbStructType.getValue() : "Stack";
        
        // Batasi ukuran tumpukan/antrean maksimum 8 elemen agar muat di canvas visualisasi
        if (structDeque.size() >= 8) {
            lblStructStatus.setText("[!] " + type + " penuh (maksimum 8)!");
            return;
        }
        
        // Stack: masukkan data ke atas (push)
        // Queue: masukkan data ke belakang barisan (addLast)
        if ("Stack".equals(type)) structDeque.push(val);
        else structDeque.addLast(val);
        
        if (tfStructInput != null) tfStructInput.clear();
        lblStructStatus.setText(("Stack".equals(type) ? "Push" : "Enqueue") + " \"" + val + "\" berhasil");
        
        // Render ulang perubahan bentuk struktur data ke canvas
        drawStructure();
    }

    /**
     * Mengeluarkan data terdepan (Pop untuk Stack atau Dequeue untuk Queue).
     */
    @FXML private void onStructPop() {
        String type = cmbStructType != null ? cmbStructType.getValue() : "Stack";
        if (structDeque.isEmpty()) { 
            lblStructStatus.setText("[!] " + type + " kosong!"); 
            return; 
        }
        
        // Stack: keluarkan dari atas (LIFO - pop)
        // Queue: keluarkan dari barisan terdepan (FIFO - pollFirst)
        String removed = "Stack".equals(type) ? structDeque.pop() : structDeque.pollFirst();
        lblStructStatus.setText(("Stack".equals(type) ? "Pop" : "Dequeue") + " \"" + removed + "\" berhasil");
        
        // Render ulang perubahan bentuk struktur data ke canvas
        drawStructure();
    }

    /**
     * Mengosongkan seluruh isi Stack/Queue.
     */
    @FXML private void onStructClear() {
        structDeque.clear();
        lblStructStatus.setText("Struktur data dibersihkan");
        drawStructure();
    }

    private void drawStructure() {
        if (structCanvas == null) return;
        GraphicsContext gc = structCanvas.getGraphicsContext2D();
        double w = structCanvas.getWidth(), h = structCanvas.getHeight();
        gc.clearRect(0, 0, w, h);

        String type = cmbStructType != null ? cmbStructType.getValue() : "Stack";
        List<String> items = new ArrayList<>(structDeque);

        double boxW = 72, boxH = 44, startX = (w - boxW) / 2;
        if ("Stack".equals(type)) {
            for (int i = 0; i < items.size(); i++) {
                double y = h - 50 - i * (boxH + 6);
                gc.setFill(i == 0 ? Color.web("#C62828") : Color.web("#3D3535"));
                gc.fillRoundRect(startX, y, boxW, boxH, 10, 10);
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Inter", FontWeight.BOLD, 15));
                gc.fillText(items.get(i), startX + 28, y + 27);
            }
            gc.setFill(Color.web("#8C7B7B"));
            gc.setFont(Font.font("Inter", 12));
            gc.fillText("TOP ↑", startX + 22, h - 50 - items.size() * (boxH + 6) - 8);
        } else {
            for (int i = 0; i < items.size(); i++) {
                double x = 30 + i * (boxW + 8);
                gc.setFill(i == 0 ? Color.web("#C62828") : Color.web("#3D3535"));
                gc.fillRoundRect(x, h / 2 - boxH / 2, boxW, boxH, 10, 10);
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Inter", FontWeight.BOLD, 15));
                gc.fillText(items.get(i), x + 28, h / 2 + 6);
            }
            if (!items.isEmpty()) {
                gc.setFill(Color.web("#8C7B7B"));
                gc.setFont(Font.font("Inter", 12));
                gc.fillText("FRONT →", 32, h / 2 - boxH / 2 - 10);
            }
        }
    }

    // ========== DRAG & DROP CHALLENGE ==========
    private void setupDragDrop() {
        if (cmbDragAlgo != null) {
            cmbDragAlgo.getItems().addAll("Bubble Sort", "Binary Search", "BFS", "DFS");
            cmbDragAlgo.setValue("Bubble Sort");
            cmbDragAlgo.setOnAction(e -> loadDragChallenge());
            loadDragChallenge();
        }
    }

    @FXML private void onDragAlgoChange() { loadDragChallenge(); }

    private void loadDragChallenge() {
        if (dragSourceBox == null) return;
        dragSourceBox.getChildren().clear();
        dragTargetBox.getChildren().clear();
        dragOrder.clear();
        dragItems.clear();
        if (lblDragResult != null) lblDragResult.setText("");

        String algo = cmbDragAlgo.getValue();
        List<String> steps = getDragSteps(algo);
        List<String> shuffled = new ArrayList<>(steps);
        Collections.shuffle(shuffled);

        for (String step : shuffled) {
            Label lbl = createDragLabel(step, steps);
            dragSourceBox.getChildren().add(lbl);
            dragItems.add(lbl);
        }
        dragOrder.addAll(steps);
    }

    private Label createDragLabel(String text, List<String> correct) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("drag-item");
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setWrapText(true);
        lbl.setOnMouseClicked(e -> {
            if (dragTargetBox.getChildren().size() < dragOrder.size()) {
                dragSourceBox.getChildren().remove(lbl);
                dragTargetBox.getChildren().add(lbl);
                lbl.getStyleClass().add("drag-item-placed");
            }
        });
        return lbl;
    }

    /**
     * Memeriksa kecocokan urutan langkah yang disusun oleh pengguna dengan kunci jawaban.
     */
    @FXML private void onCheckDrag() {
        if (dragTargetBox == null) return;
        
        // Membaca daftar teks langkah-langkah yang ditaruh pengguna di box target
        List<String> placed = dragTargetBox.getChildren().stream()
            .filter(n -> n instanceof Label)
            .map(n -> ((Label) n).getText())
            .toList();
            
        // Bandingkan dengan urutan langkah yang asli/benar
        boolean correct = placed.equals(dragOrder);
        if (lblDragResult != null) {
            lblDragResult.setText(correct
                ? "✅ Urutan BENAR! Kamu paham algoritma ini!"
                : "❌ Urutan salah. Coba lagi!");
            lblDragResult.getStyleClass().removeAll("text-success", "text-red");
            lblDragResult.getStyleClass().add(correct ? "text-success" : "text-red");
        }
    }

    /**
     * Mengatur ulang tantangan drag-drop (mengacak kembali langkah-langkah).
     */
    @FXML private void onResetDrag() { loadDragChallenge(); }

    private List<String> getDragSteps(String algo) {
        return switch (algo) {
            case "Bubble Sort" -> List.of(
                "1. Mulai dari elemen pertama",
                "2. Bandingkan dua elemen berdekatan",
                "3. Jika elemen kiri > kanan, tukar posisi",
                "4. Lanjutkan ke pasangan berikutnya",
                "5. Ulangi untuk semua pass hingga terurut"
            );
            case "Binary Search" -> List.of(
                "1. Pastikan array sudah terurut",
                "2. Tentukan indeks low, mid, high",
                "3. Bandingkan target dengan elemen tengah (mid)",
                "4. Jika target < mid, cari di bagian kiri",
                "5. Jika target > mid, cari di bagian kanan",
                "6. Ulangi sampai target ditemukan atau low > high"
            );
            case "BFS" -> List.of(
                "1. Tambahkan node awal ke dalam Queue",
                "2. Tandai node awal sebagai visited",
                "3. Ambil node dari depan Queue",
                "4. Kunjungi dan proses node tersebut",
                "5. Tambahkan semua tetangga yang belum visited ke Queue",
                "6. Ulangi sampai Queue kosong"
            );
            case "DFS" -> List.of(
                "1. Tambahkan node awal ke dalam Stack",
                "2. Tandai node awal sebagai visited",
                "3. Ambil node dari atas Stack",
                "4. Kunjungi dan proses node tersebut",
                "5. Tambahkan tetangga yang belum visited ke Stack",
                "6. Ulangi sampai Stack kosong"
            );
            default -> List.of();
        };
    }

    @FXML
    private void onBack() throws Exception {
        App.navigateTo("main-menu");
    }
}
