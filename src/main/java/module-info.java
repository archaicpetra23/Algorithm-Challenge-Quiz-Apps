/**
 * Module-info untuk proyek Algorithm Challenge Quiz.
 * Mengonfigurasi Java Platform Module System (JPMS) untuk mengatur visibilitas package dan deklarasi dependensi modul eksternal.
 */
module com.algorithmquiz {
    // Modul JavaFX untuk visualisasi UI dan layouting
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    // Modul Hibernate ORM dan JPA untuk interaksi dengan SQLite
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires net.bytebuddy;
    requires java.naming;
    requires jakarta.xml.bind;
    
    // Modul Ikonli untuk merender icon FontAwesome secara native di JavaFX
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    uses org.kordamp.ikonli.IkonHandler;

    // Membuka (opens) package agar JavaFX FXML Loader dapat memuat controller secara refleksif
    opens com.algorithmquiz to javafx.fxml;
    opens com.algorithmquiz.controller to javafx.fxml;
    
    // Membuka package model ke Hibernate ORM agar pemetaan entity JPA bekerja
    opens com.algorithmquiz.model to javafx.fxml, org.hibernate.orm.core;
    opens com.algorithmquiz.database to org.hibernate.orm.core;

    // Mengekspor package agar dapat diakses oleh JVM runtime JavaFX
    exports com.algorithmquiz;
    exports com.algorithmquiz.controller;
    exports com.algorithmquiz.model;
}
