package com.algorithmquiz.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * TimerManager: Mengelola countdown timer per soal.
 * SRP: hanya bertanggung jawab timer logic.
 * Sesuai PRD FR-11: timer hitung mundur, jika habis soal dilewati.
 */
public class TimerManager {

    private Timeline timeline;
    private int timeRemaining;
    private int totalTime;

    private IntConsumer onTick;       // dipanggil setiap detik dengan sisa waktu
    private Runnable onTimeout;       // dipanggil saat waktu habis

    public TimerManager() {}

    /**
     * Mulai timer baru.
     * @param seconds total waktu dalam detik
     * @param onTick callback setiap detik (sisa waktu)
     * @param onTimeout callback saat waktu habis
     */
    public void start(int seconds, IntConsumer onTick, Runnable onTimeout) {
        stop();
        this.totalTime = seconds;
        this.timeRemaining = seconds;
        this.onTick = onTick;
        this.onTimeout = onTimeout;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            if (onTick != null) onTick.accept(timeRemaining);
            if (timeRemaining <= 0) {
                stop();
                if (onTimeout != null) onTimeout.run();
            }
        }));
        timeline.setCycleCount(seconds);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    public void pause() {
        if (timeline != null) timeline.pause();
    }

    public void resume() {
        if (timeline != null) timeline.play();
    }

    public int getTimeRemaining() { return timeRemaining; }
    public int getTotalTime() { return totalTime; }

    /**
     * Hitung progress (0.0 → 1.0) untuk ring timer.
     */
    public double getProgress() {
        if (totalTime == 0) return 0;
        return (double) timeRemaining / totalTime;
    }
}
