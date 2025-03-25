module com.example.agarioclientsae {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.agarioclientsae to javafx.fxml;
    exports com.example.agarioclientsae.player;
    opens com.example.agarioclientsae.player to javafx.fxml;
    exports com.example.agarioclientsae.AI;
    opens com.example.agarioclientsae.AI to javafx.fxml;
    exports com.example.agarioclientsae.factories;
    opens com.example.agarioclientsae.factories to javafx.fxml;
    exports com.example.agarioclientsae.app;
    opens com.example.agarioclientsae.app to javafx.fxml;
    exports com.example.agarioclientsae.worldElements;
    opens com.example.agarioclientsae.worldElements to javafx.fxml;
}