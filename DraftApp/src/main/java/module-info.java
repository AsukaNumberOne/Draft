module com.example.draftapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.draftapp to javafx.fxml;
    exports com.example.draftapp;
}