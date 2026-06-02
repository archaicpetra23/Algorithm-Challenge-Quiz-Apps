package com.algorithmquiz.model;

import jakarta.persistence.*;

/**
 * Question: Kelas entitas (Entity) yang merepresentasikan tabel "questions" dalam database.
 * Digunakan oleh Hibernate ORM untuk memetakan kolom database secara otomatis ke objek Java.
 * Menerapkan prinsip dasar OOP: Encapsulation (Enkapsulasi) dengan variabel private dan metode getter/setter.
 */
@Entity
@Table(name = "questions") // Menentukan bahwa objek ini disimpan ke tabel bernama "questions"
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID primer (primary key)
    private Long id;

    @Column(nullable = false)
    private String level;         // Tingkat kesulitan soal ("easy", "medium", "hard")

    @Column(nullable = false)
    private String type;          // Tipe kuis ("multiple_choice" (PG), "dragdrop", dll.)

    @Column(nullable = false, length = 1000)
    private String questionText;  // Isi teks pertanyaan kuis

    @Column(nullable = false)
    private String optionA;       // Pilihan jawaban A

    @Column(nullable = false)
    private String optionB;       // Pilihan jawaban B

    @Column(nullable = false)
    private String optionC;       // Pilihan jawaban C

    @Column(nullable = false)
    private String optionD;       // Pilihan jawaban D

    @Column(nullable = false)
    private String correctAnswer; // Kunci jawaban yang benar (bernilai "A", "B", "C", atau "D")

    @Column(length = 500)
    private String explanation;   // Teks pembahasan/penjelasan jawaban soal

    @Column
    private String category;      // Kategori topik soal (misal: "sorting", "graph", "structure")

    @Column
    private int timeLimit = 30;   // Batas waktu pengerjaan soal (default 30 detik)

    // Constructor kosong wajib untuk Hibernate ORM
    public Question() {}

    /**
     * Constructor lengkap untuk pembuatan objek soal kuis baru.
     */
    public Question(String level, String type, String questionText,
                    String optionA, String optionB, String optionC, String optionD,
                    String correctAnswer, String explanation, String category, int timeLimit) {
        this.level = level;
        this.type = type;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.category = category;
        this.timeLimit = timeLimit;
    }

    // ========== GETTERS & SETTERS (Akses Data Terenkapsulasi) ==========
    public Long getId() { return id; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    
    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    
    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    
    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

    /**
     * Membantu mengambil isi teks jawaban berdasarkan kunci string huruf pilihan ("A"-"D").
     */
    public String getOptionByKey(String key) {
        return switch (key.toUpperCase()) {
            case "A" -> optionA;
            case "B" -> optionB;
            case "C" -> optionC;
            case "D" -> optionD;
            default -> "";
        };
    }
}
