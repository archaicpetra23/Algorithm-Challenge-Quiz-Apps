package com.algorithmquiz.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * TimerManager: Kelas pengelola hitung mundur (countdown timer) interaktif per soal kuis.
 * Menerapkan Single Responsibility Principle (SRP): Hanya fokus mengurus logika penghitungan waktu.
 * Sesuai PRD FR-11: Menyediakan timer hitung mundur. Jika waktu habis, soal dilewati otomatis.
 */
public class TimerManager {

    // Objek Timeline dari JavaFX untuk menjalankan tugas berulang setiap detik secara asinkron
    private Timeline timeline;
    
    // Menyimpan sisa waktu pengerjaan soal saat ini (dalam detik)
    private int timeRemaining;
    
    // Menyimpan total waktu pengerjaan awal yang diberikan untuk soal (dalam detik)
    private int totalTime;

    // Callback/Event listener yang dipanggil setiap detik (mengirimkan sisa waktu ke UI untuk di-update)
    private IntConsumer onTick;
    
    // Callback/Event listener yang dipanggil ketika waktu pengerjaan habis (memicu aksi ganti soal)
    private Runnable onTimeout;

    public TimerManager() {}

    /**
     * Mulai timer baru untuk soal yang sedang aktif.
     * @param seconds total waktu pengerjaan dalam detik (misal: 30 detik untuk level Easy)
     * @param onTick aksi yang dipanggil setiap 1 detik untuk memperbarui teks timer di UI
     * @param onTimeout aksi yang dipanggil saat waktu habis (misal: memicu auto-skip ke soal berikutnya)
     */
    public void start(int seconds, IntConsumer onTick, Runnable onTimeout) {
        // Hentikan timer sebelumnya jika masih berjalan agar tidak terjadi tabrakan timer
        stop();
        this.totalTime = seconds;
        this.timeRemaining = seconds;
        this.onTick = onTick;
        this.onTimeout = onTimeout;

        // Mendefinisikan KeyFrame berdurasi 1 detik untuk menjalankan pengurangan sisa waktu secara berulang
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--; // Kurangi sisa waktu sebanyak 1 detik
            
            // Panggil aksi tick untuk meng-update UI
            if (onTick != null) onTick.accept(timeRemaining);
            
            // Jika waktu sudah habis (0 detik atau kurang), hentikan timer dan jalankan aksi timeout
            if (timeRemaining <= 0) {
                stop();
                if (onTimeout != null) onTimeout.run();
            }
        }));
        
        // Atur agar timeline berulang sebanyak total detik yang ditentukan
        timeline.setCycleCount(seconds);
        
        // Mulai jalankan timer hitung mundur
        timeline.play();
    }

    /**
     * Menghentikan timer dan menghapus objek timeline secara bersih.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    /**
     * Menunda (pause) timer (dapat digunakan jika ada dialog pop-up konfirmasi keluar/jeda).
     */
    public void pause() {
        if (timeline != null) timeline.pause();
    }

    /**
     * Melanjutkan kembali (resume) timer yang sedang ditunda.
     */
    public void resume() {
        if (timeline != null) timeline.play();
    }

    // Mengambil sisa waktu saat ini dalam detik (dipakai untuk perhitungan skor/bonus waktu)
    public int getTimeRemaining() { return timeRemaining; }
    
    // Mengambil total waktu awal soal
    public int getTotalTime() { return totalTime; }

    /**
     * Menghitung persentase progress waktu (nilai antara 0.0 sampai 1.0)
     * yang berguna jika ingin menampilkan progress bar/lingkaran waktu di antarmuka pengguna.
     */
    public double getProgress() {
        if (totalTime == 0) return 0;
        return (double) timeRemaining / totalTime;
    }
}
