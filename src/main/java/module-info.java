module com.algorithmquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires net.bytebuddy;
    requires java.naming;
    requires jakarta.xml.bind;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    uses org.kordamp.ikonli.IkonHandler;

    opens com.algorithmquiz to javafx.fxml;
    opens com.algorithmquiz.controller to javafx.fxml;
    opens com.algorithmquiz.model to javafx.fxml, org.hibernate.orm.core;
    opens com.algorithmquiz.database to org.hibernate.orm.core;

    exports com.algorithmquiz;
    exports com.algorithmquiz.controller;
    exports com.algorithmquiz.model;
}
