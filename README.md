# Algorithm Challenge Quiz

Aplikasi edukasi interaktif berbasis Java, JavaFX, dan SQLite untuk membantu proses pemahaman algoritma dan struktur data melalui kuis berjenjang, modul visualisasi dinamis, serta tantangan interaktif.

---

## Panduan Menjalankan Aplikasi

### Prasyarat Sistem
* Java Development Kit (JDK) versi 21 atau yang lebih baru
* Apache Maven versi 3.9 atau yang lebih baru

### Metode 1: Menggunakan Script Peluncur (Tanpa Terminal)
Anda dapat langsung mengeksekusi aplikasi menggunakan berkas peluncur yang telah disediakan di dalam direktori proyek:
* **Sistem Operasi Linux:** Jalankan berkas `run.sh` (pastikan izin eksekusi aktif) atau klik dua kali melalui pengelola berkas.
* **Sistem Operasi Windows:** Klik dua kali berkas `run.bat` untuk menjalankan otomatis.

### Metode 2: Melalui Terminal secara Manual
Jalankan perintah berikut di dalam direktori utama proyek:
```bash
cd "/home/razan/Documents/Algorithm Challenge Quiz"
mvn javafx:run
```

---

## Fitur Utama Sistem

| Fitur | Deskripsi Teknis |
|---|---|
| Evaluasi 3 Tingkat Kesulitan | Menyediakan kategori soal Easy, Medium, dan Hard dengan masing-masing 10 soal uji. |
| Pengukur Waktu Batas (Timer) | Mekanisme hitung mundur interaktif per soal yang akan melewati pertanyaan secara otomatis jika batas waktu habis. |
| Visualisasi Algoritma Sorting | Demonstrasi animasi interaktif untuk Bubble Sort, Selection Sort, dan Insertion Sort dengan kontrol kecepatan. |
| Penelusuran Graf (Graph Traversal) | Visualisasi langkah demi langkah penelusuran Breadth-First Search (BFS) dan Depth-First Search (DFS). |
| Simulasi Struktur Data | Representasi visual operasi Stack (Push/Pop) dan Queue (Enqueue/Dequeue) dengan opsi nilai preset cepat. |
| Tantangan Drag and Drop | Modul interaktif untuk menguji pemahaman logika pengurutan langkah-langkah algoritma secara berurutan. |
| Papan Skor (Leaderboard) | Penyimpanan riwayat skor terbaik pemain secara permanen menggunakan SQLite database dengan filter berdasarkan tingkat kesulitan. |
| Analisis Hasil Evaluasi | Penilaian performa akhir (Grade A-E) beserta tinjauan penjelasan jawaban pada akhir sesi kuis. |

---

## Arsitektur Sistem

Aplikasi ini dirancang mengikuti prinsip Pemrograman Berorientasi Objek (OOP) dengan pola arsitektur berikut:

```
JavaFX (FXML + CSS)
    │
    ├── Controllers (6 Layar Utama)
    │       ├── MainMenuController
    │       ├── SetupController
    │       ├── QuizController
    │       ├── ResultController
    │       ├── LeaderboardController
    │       └── LearnController
    │
    ├── Manager (Penerapan Single Responsibility Principle - SRP)
    │       ├── QuizManager    - Mengelola status sesi kuis
    │       ├── ScoreManager   - Menghitung perolehan skor dan akurasi waktu
    │       └── TimerManager   - Mengatur utilitas hitung mundur sesi kuis
    │
    ├── Factory Pattern
    │       └── QuestionFactory - Instansiasi dinamis objek kuis berdasarkan jenis tipe soal
    │
    ├── Strategy Pattern
    │       ├── MultipleChoiceStrategy - Strategi evaluasi pilihan ganda
    │       └── DragDropStrategy       - Strategi evaluasi penyusunan urutan
    │
    └── Database Layer (Hibernate ORM + SQLite)
            ├── QuestionRepository
            └── LeaderboardRepository
```

---

## Palet Warna Desain (Clean & Modern)

| Komponen Desain | Nilai Hex | Representasi Visual |
|---|---|---|
| Latar Belakang Utama | `#F5EEEE` | Abu-abu Terang Hangat |
| Aksen Utama (Accent Red) | `#C62828` | Merah Gelap Kontras |
| Teks Utama | `#000000` | Hitam |
| Teks Sekunder | `#3D3535` | Abu-abu Gelap |
| Garis Batas / Border | `#E8E0E0` | Abu-abu Halus |

---

## Struktur Direktori Proyek

```
Algorithm Challenge Quiz/
├── pom.xml
├── AGENT.md          - Panduan sinkronisasi konteks agen AI
├── README.md         - Dokumentasi teknis proyek
├── algorithm_quiz.db - Basis data SQLite (dihasilkan otomatis saat kompilasi perdana)
└── src/main/
    ├── java/com/algorithmquiz/
    │   ├── App.java
    │   ├── model/
    │   ├── database/
    │   ├── repository/
    │   ├── factory/
    │   ├── strategy/
    │   ├── manager/
    │   ├── controller/
    │   └── util/
    └── resources/
        ├── fxml/     - Berkas tata letak antarmuka JavaFX (FXML)
        └── css/      - Berkas penataan gaya antarmuka (app.css)
```

---

## Spesifikasi Soal Evaluasi

| Tingkat Kesulitan | Jumlah Soal | Fokus Topik Uji |
|---|---|---|
| Easy | 10 | Dasar Algoritma, Analisis Notasi Big-O Dasar, Konsep Awal Stack & Queue |
| Medium | 10 | Cara Kerja Bubble/Selection/Insertion Sort, Mekanisme BFS & DFS |
| Hard | 10 | Logika Merge/Quick Sort, Penerapan Hash Table, Teori Lintasan Terpendek Dijkstra |

---

## Proyek UAS Pemrograman Berorientasi Objek
