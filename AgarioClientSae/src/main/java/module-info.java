module com.example.client.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.client.app to javafx.fxml;
    exports com.example.client.app;
}
