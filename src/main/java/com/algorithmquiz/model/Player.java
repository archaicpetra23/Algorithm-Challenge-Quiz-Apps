package com.algorithmquiz.model;

/**
 * Player: Kelas model untuk menyimpan status (state) pemain selama sesi kuis berlangsung.
 * Menerapkan prinsip dasar Pemrograman Berorientasi Objek (OOP): Encapsulation (Enkapsulasi) 
 * dengan membungkus data dalam variabel private dan menyediakan metode akses (Getter dan Setter).
 */
public class Player {

    // Variabel enkapsulasi data pemain
    private String name;                  // Nama pemain
    private String selectedLevel;         // Tingkat kesulitan yang dipilih (easy, medium, hard)
    private int score;                    // Total perolehan skor pemain
    private int correctCount;             // Jumlah soal yang berhasil dijawab dengan benar
    private int currentQuestionIndex;     // Indeks soal kuis yang sedang dihadapi pemain (0 s.d 9)

    // Constructor kosong (diperlukan oleh Hibernate ORM/mapping)
    public Player() {}

    /**
     * Constructor dengan parameter untuk menginisialisasi pemain baru.
     * @param name nama pemain yang diinput pada setup kuis
     * @param selectedLevel tingkat kesulitan kuis yang dipilih
     */
    public Player(String name, String selectedLevel) {
        this.name = name;
        this.selectedLevel = selectedLevel;
        this.score = 0;                   // Skor awal diatur mulai dari 0
        this.correctCount = 0;            // Jumlah benar awal diatur 0
        this.currentQuestionIndex = 0;    // Dimulai dari pertanyaan indeks ke-0 (nomor 1)
    }

    /**
     * Menambahkan poin skor ke total skor pemain.
     * @param points jumlah poin yang diperoleh dari strategi kuis
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Meningkatkan jumlah jawaban benar pemain sebesar 1.
     */
    public void incrementCorrect() {
        this.correctCount++;
    }

    /**
     * Melangkah ke indeks pertanyaan berikutnya.
     */
    public void nextQuestion() {
        this.currentQuestionIndex++;
    }

    /**
     * Mengatur ulang (reset) seluruh status pemain ke kondisi semula.
     */
    public void reset() {
        this.score = 0;
        this.correctCount = 0;
        this.currentQuestionIndex = 0;
    }

    // ========== GETTER & SETTER (Akses Data Terenkapsulasi) ==========
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSelectedLevel() { return selectedLevel; }
    public void setSelectedLevel(String selectedLevel) { this.selectedLevel = selectedLevel; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getCorrectCount() { return correctCount; }
    
    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
}
