module com.example.szamolo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires json.simple;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.junit.jupiter.api;

    opens com.example.szamolo to javafx.fxml;
    exports com.example.szamolo;
}