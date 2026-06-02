package com.algorithmquiz.util;

import com.algorithmquiz.factory.QuestionFactory;
import com.algorithmquiz.model.Question;
import com.algorithmquiz.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSeeder: Mengisi database dengan 30 soal default jika masih kosong.
 * 10 soal Easy + 10 Medium + 10 Hard.
 * Sesuai PRD: bank soal berjenjang kesulitan.
 */
public class DataSeeder {

    private static final QuestionRepository repo = new QuestionRepository();

    /**
     * Memeriksa isi database. Jika masih kosong, metode ini akan memasukkan 30 pertanyaan kuis bawaan.
     */
    public static void seedIfEmpty() {
        // Cek apakah database sudah terisi soal sebelumnya
        if (repo.countAll() > 0) {
            System.out.println("[Seeder] Database sudah terisi soal. Seeding dilewati.");
            return;
        }
        System.out.println("[Seeder] Menanamkan (seeding) 30 soal awal...");
        List<Question> all = new ArrayList<>();
        
        // Gabungkan kumpulan soal Easy, Medium, dan Hard
        all.addAll(createEasyQuestions());
        all.addAll(createMediumQuestions());
        all.addAll(createHardQuestions());
        
        // Simpan setiap objek soal ke dalam database SQLite via repository
        all.forEach(repo::save);
        System.out.println("[Seeder] Selesai! " + all.size() + " soal berhasil ditambahkan.");
    }

    // ===================== EASY (10 soal) =====================
    private static List<Question> createEasyQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(QuestionFactory.easyMC(
            "Struktur data apa yang menggunakan prinsip LIFO (Last In First Out)?",
            "Queue", "Stack", "Array", "Tree",
            "B", "Stack bekerja dengan LIFO: elemen yang terakhir masuk adalah yang pertama keluar. Contoh: tumpukan piring.", "structure"
        ));

        list.add(QuestionFactory.easyMC(
            "Struktur data apa yang menggunakan prinsip FIFO (First In First Out)?",
            "Stack", "Tree", "Queue", "Graph",
            "C", "Queue bekerja dengan FIFO: elemen yang pertama masuk adalah yang pertama keluar. Contoh: antrian kasir.", "structure"
        ));

        list.add(QuestionFactory.easyMC(
            "Berapa kompleksitas waktu Linear Search pada kasus terburuk (worst case)?",
            "O(1)", "O(log n)", "O(n)", "O(n²)",
            "C", "Linear Search memeriksa setiap elemen satu per satu, sehingga worst case adalah O(n) — harus cek semua elemen.", "complexity"
        ));

        list.add(QuestionFactory.easyMC(
            "Operasi apa yang digunakan untuk menambahkan elemen ke dalam Stack?",
            "Enqueue", "Push", "Insert", "Add",
            "B", "Push adalah operasi standar untuk menambah elemen ke atas (top) Stack.", "structure"
        ));

        list.add(QuestionFactory.easyMC(
            "Operasi apa yang digunakan untuk menambahkan elemen ke dalam Queue?",
            "Push", "Insert", "Enqueue", "Append",
            "C", "Enqueue adalah operasi standar untuk menambah elemen ke belakang (rear) Queue.", "structure"
        ));

        list.add(QuestionFactory.easyMC(
            "Notasi Big-O O(1) berarti kompleksitas waktu bersifat...",
            "Linear", "Logaritmik", "Konstan", "Kuadratik",
            "C", "O(1) artinya Constant Time — waktu eksekusi tidak bergantung pada ukuran input.", "complexity"
        ));

        list.add(QuestionFactory.easyMC(
            "Algoritma apa yang membagi ruang pencarian menjadi dua bagian setiap iterasinya?",
            "Linear Search", "Binary Search", "Bubble Sort", "Insertion Sort",
            "B", "Binary Search membagi array terurut menjadi dua setiap kali, sehingga kompleksitasnya O(log n).", "sorting"
        ));

        list.add(QuestionFactory.easyMC(
            "Indeks pertama (awal) sebuah array di Java dimulai dari angka berapa?",
            "1", "-1", "0", "2",
            "C", "Array di Java (dan sebagian besar bahasa pemrograman) menggunakan indeks berbasis 0 (zero-indexed).", "basic"
        ));

        list.add(QuestionFactory.easyMC(
            "Apa yang dimaksud dengan rekursi dalam pemrograman?",
            "Perulangan menggunakan for loop", "Fungsi yang memanggil dirinya sendiri", "Perulangan menggunakan while", "Fungsi tanpa return",
            "B", "Rekursi adalah teknik di mana sebuah fungsi memanggil dirinya sendiri dengan input yang lebih kecil hingga mencapai base case.", "basic"
        ));

        list.add(QuestionFactory.easyMC(
            "Berapa kompleksitas waktu Bubble Sort pada kasus terburuk?",
            "O(1)", "O(n)", "O(log n)", "O(n²)",
            "D", "Bubble Sort melakukan n-1 pass dengan n-1 perbandingan tiap pass, menghasilkan kompleksitas O(n²) pada worst case.", "sorting"
        ));

        return list;
    }

    // ===================== MEDIUM (10 soal) =====================
    private static List<Question> createMediumQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(QuestionFactory.mediumMC(
            "Pada Bubble Sort, array [5, 3, 1, 4, 2] setelah pass pertama selesai, elemen paling akhir (posisi ke-5) bernilai?",
            "2", "3", "4", "5",
            "D", "Pada pass pertama Bubble Sort, elemen terbesar (5) akan 'menggelembung' ke posisi terakhir melalui serangkaian swap.", "sorting"
        ));

        list.add(QuestionFactory.mediumMC(
            "Algoritma BFS (Breadth-First Search) menggunakan struktur data apa untuk menyimpan node yang akan dikunjungi?",
            "Stack", "Queue", "Tree", "Array",
            "B", "BFS menggunakan Queue (FIFO) untuk memastikan semua node pada level yang sama dikunjungi sebelum ke level berikutnya.", "graph"
        ));

        list.add(QuestionFactory.mediumMC(
            "Algoritma DFS (Depth-First Search) menggunakan struktur data apa secara implisit (rekursi) atau eksplisit?",
            "Queue", "Tree", "Stack", "Heap",
            "C", "DFS menggunakan Stack (LIFO) — secara eksplisit atau melalui call stack rekursi — untuk menelusuri jalur sedalam mungkin.", "graph"
        ));

        list.add(QuestionFactory.mediumMC(
            "Pada setiap iterasi Selection Sort, apa yang dilakukan algoritma terhadap subarray yang belum terurut?",
            "Mencari elemen maksimum", "Mencari elemen minimum", "Membagi array", "Menukar elemen acak",
            "B", "Selection Sort mencari elemen minimum dari subarray yang belum terurut, lalu menempatkannya di posisi yang benar.", "sorting"
        ));

        list.add(QuestionFactory.mediumMC(
            "Bagaimana cara kerja Insertion Sort dalam mengurutkan elemen?",
            "Membandingkan semua pasangan elemen", "Menyisipkan elemen ke posisi yang tepat di bagian yang sudah terurut", "Memilih elemen minimum tiap iterasi", "Membagi dan menaklukkan",
            "B", "Insertion Sort mengambil elemen satu per satu dari bagian yang belum terurut dan menyisipkannya ke posisi yang benar di bagian yang sudah terurut.", "sorting"
        ));

        list.add(QuestionFactory.mediumMC(
            "Graph traversal mana yang mengeksplorasi SEMUA tetangga langsung sebelum pindah ke level berikutnya?",
            "DFS", "BFS", "Dijkstra", "Prim",
            "B", "BFS (Breadth-First Search) menjelajahi semua node pada kedalaman/level yang sama sebelum melanjutkan ke level berikutnya.", "graph"
        ));

        list.add(QuestionFactory.mediumMC(
            "Berapa kompleksitas waktu Insertion Sort pada kasus terburuk (array terurut terbalik)?",
            "O(n log n)", "O(n)", "O(n²)", "O(log n)",
            "C", "Pada array terurut terbalik, setiap elemen harus dibandingkan dan digeser melewati semua elemen sebelumnya → O(n²).", "sorting"
        ));

        list.add(QuestionFactory.mediumMC(
            "Pada BFS dari node A dengan edge: A→B, A→C, B→D, C→E. Urutan kunjungan yang benar adalah?",
            "A, B, D, C, E", "A, B, C, D, E", "A, C, E, B, D", "A, D, B, E, C",
            "B", "BFS: kunjungi A, lalu semua tetangga A (B dan C), lalu tetangga B (D), lalu tetangga C (E) → A, B, C, D, E.", "graph"
        ));

        list.add(QuestionFactory.mediumMC(
            "Berapa banyak perbandingan yang dilakukan pada pass pertama Bubble Sort untuk array berisi 5 elemen?",
            "3", "4", "5", "6",
            "B", "Untuk array n elemen, pass pertama melakukan n-1 perbandingan. Untuk 5 elemen: 5-1 = 4 perbandingan.", "sorting"
        ));

        list.add(QuestionFactory.mediumMC(
            "Selection Sort diterapkan pada [64, 25, 12, 22, 11]. Bagaimana array setelah iterasi PERTAMA selesai?",
            "[11, 25, 12, 22, 64]", "[25, 64, 12, 22, 11]", "[12, 25, 64, 22, 11]", "[11, 64, 25, 22, 12]",
            "A", "Iterasi pertama: temukan minimum (11) di seluruh array, tukar dengan elemen pertama (64) → [11, 25, 12, 22, 64].", "sorting"
        ));

        return list;
    }

    // ===================== HARD (10 soal) =====================
    private static List<Question> createHardQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(QuestionFactory.hardMC(
            "Berapa kompleksitas waktu algoritma Merge Sort pada semua kasus (best, average, worst)?",
            "O(n²)", "O(n)", "O(n log n)", "O(log n)",
            "C", "Merge Sort selalu O(n log n) karena selalu membagi array menjadi dua (log n level) dan merge membutuhkan O(n) per level.", "sorting"
        ));

        list.add(QuestionFactory.hardMC(
            "Pada kasus terburuk (worst case), berapa kompleksitas waktu Quick Sort?",
            "O(n log n)", "O(n²)", "O(n)", "O(log n)",
            "B", "Quick Sort worst case O(n²) terjadi ketika pivot selalu dipilih sebagai elemen terkecil/terbesar (array sudah terurut).", "sorting"
        ));

        list.add(QuestionFactory.hardMC(
            "Rata-rata (average case) kompleksitas waktu operasi lookup pada Hash Table adalah?",
            "O(n)", "O(log n)", "O(n²)", "O(1)",
            "D", "Hash Table menggunakan fungsi hash untuk langsung mengakses lokasi data → O(1) rata-rata. Worst case O(n) jika banyak collision.", "complexity"
        ));

        list.add(QuestionFactory.hardMC(
            "Space complexity DFS pada pohon dengan tinggi h adalah?",
            "O(n)", "O(h)", "O(1)", "O(n²)",
            "B", "DFS menggunakan call stack dengan kedalaman maksimum h (tinggi pohon) → space complexity O(h). Pada pohon seimbang, h = O(log n).", "graph"
        ));

        list.add(QuestionFactory.hardMC(
            "Algoritma sorting mana yang bersifat STABLE (urutan elemen sama yang setara tetap terjaga)?",
            "Quick Sort", "Selection Sort", "Merge Sort", "Heap Sort",
            "C", "Merge Sort adalah algoritma sorting stable yang efisien. Quick Sort dan Heap Sort umumnya tidak stable.", "sorting"
        ));

        list.add(QuestionFactory.hardMC(
            "Algoritma sorting mana yang TIDAK berbasis perbandingan (non-comparison based)?",
            "Merge Sort", "Quick Sort", "Counting Sort", "Heap Sort",
            "C", "Counting Sort tidak membandingkan elemen secara langsung, melainkan menghitung frekuensi kemunculan → bisa mencapai O(n+k).", "sorting"
        ));

        list.add(QuestionFactory.hardMC(
            "BFS pada graph dengan V vertex dan E edge memiliki kompleksitas waktu?",
            "O(V)", "O(E)", "O(V + E)", "O(V × E)",
            "C", "BFS mengunjungi setiap vertex sekali O(V) dan memeriksa setiap edge sekali O(E), sehingga total O(V + E).", "graph"
        ));

        list.add(QuestionFactory.hardMC(
            "Amortized complexity dari operasi append pada dynamic array (seperti ArrayList) adalah?",
            "O(n)", "O(log n)", "O(n²)", "O(1)",
            "D", "Meskipun sesekali terjadi resize yang O(n), secara amortized setiap append hanya O(1) karena resize jarang terjadi.", "complexity"
        ));

        list.add(QuestionFactory.hardMC(
            "Algoritma Dijkstra digunakan untuk memecahkan masalah apa pada graph berbobot?",
            "Mengurutkan vertex", "Mencari jalur terpendek (shortest path)", "Mencari spanning tree minimum", "Mendeteksi cycle",
            "B", "Dijkstra's algorithm menemukan jalur terpendek dari satu sumber ke semua vertex lain dalam graph berbobot non-negatif.", "graph"
        ));

        list.add(QuestionFactory.hardMC(
            "Manakah yang BUKAN merupakan struktur data linear?",
            "Array", "Linked List", "Tree", "Queue",
            "C", "Tree adalah struktur data non-linear (hierarkis) karena memiliki hubungan parent-child bercabang. Array, Linked List, dan Queue adalah linear.", "structure"
        ));

        return list;
    }
}
