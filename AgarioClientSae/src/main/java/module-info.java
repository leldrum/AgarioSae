module com.example.agarioclientsae {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.agarioclientsae to javafx.fxml;
    exports com.example.agarioclientsae;
}