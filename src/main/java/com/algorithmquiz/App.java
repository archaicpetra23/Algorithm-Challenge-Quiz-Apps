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
 * Entry point aplikasi Algorithm Challenge Quiz.
 * Bertanggung jawab menginisialisasi database dan menampilkan Main Menu.
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Inisialisasi database & seed data
        DatabaseManager.init();
        DataSeeder.seedIfEmpty();

        // Load Main Menu
        navigateTo("main-menu");

        stage.setTitle("Algorithm Challenge Quiz");
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Navigasi ke scene berdasarkan nama FXML.
     * @param fxmlName nama file FXML (tanpa .fxml)
     */
    public static void navigateTo(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(
                App.class.getResource("/css/app.css")).toExternalForm());
        primaryStage.setScene(scene);
    }

    /**
     * Navigasi dengan data (menggunakan controller setter).
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
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
