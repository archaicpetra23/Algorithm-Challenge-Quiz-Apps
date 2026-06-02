package com.algorithmquiz;

import com.algorithmquiz.database.DatabaseManager;
import com.algorithmquiz.util.DataSeeder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * App: Entry point utama (Main Class) untuk menjalankan aplikasi JavaFX "Algorithm Challenge Quiz".
 * Menginisialisasi koneksi database SQLite via Hibernate, melakukan seeding data soal kuis, serta memuat scene utama.
 */
public class App extends Application {

    private static Stage primaryStage; // Window utama aplikasi JavaFX (Stage)

    /**
     * Siklus hidup (lifecycle) start JavaFX. Dijalankan otomatis setelah launch().
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Inisialisasi session factory database SQLite
        DatabaseManager.init();
        // Lakukan pengisian data soal (30 soal bawaan) jika database masih kosong
        DataSeeder.seedIfEmpty();

        // Navigasikan stage ke halaman Menu Utama (main-menu.fxml)
        navigateTo("main-menu");

        // Konfigurasi parameter tampilan jendela (window) utama
        stage.setTitle("Algorithm Challenge Quiz");
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setResizable(true);
        stage.show(); // Tampilkan jendela ke layar monitor
    }

    /**
     * Membantu mempermudah navigasi perpindahan layar berdasarkan nama file FXML.
     * Secara otomatis menyematkan stylesheet file CSS global (app.css).
     * @param fxmlName nama file layout fxml (tanpa ekstensi .fxml)
     */
    public static void navigateTo(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        // Tambahkan file CSS eksternal ke dalam scene untuk styling premium
        scene.getStylesheets().add(Objects.requireNonNull(
                App.class.getResource("/css/app.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    /**
     * Membantu navigasi perpindahan layar dengan mengembalikan objek FXMLLoader.
     * Digunakan ketika data perlu diteruskan (passing data) ke controller halaman tujuan sebelum ditampilkan.
     * @param fxmlName nama file layout fxml (tanpa ekstensi .fxml)
     * @return objek FXMLLoader aktif untuk scene yang dimuat
     */
    public static FXMLLoader loadFXML(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(
                App.class.getResource("/css/app.css")).toExternalForm());
        primaryStage.setScene(scene);
        return loader;
    }

    public static Stage getPrimaryStage() {
        // Mengembalikan instance window utama
        return primaryStage;
    }

    public static void main(String[] args) {
        // Bootstrapping aplikasi JavaFX
        launch(args);
    }
}
