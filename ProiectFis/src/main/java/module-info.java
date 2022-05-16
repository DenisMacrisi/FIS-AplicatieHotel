module com.example.proiectfis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
   // requires mysql.connector.java;

    opens com.example.proiectfis to javafx.fxml;
    exports com.example.proiectfis;
}