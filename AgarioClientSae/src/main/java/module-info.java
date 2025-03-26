module com.example.agarioclientsae {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.client.app to javafx.fxml;
    exports com.example.client.app;

    // Ajoute les exports nécessaires
    exports com.example.libraries.worldElements;
    exports com.example.libraries.factories;
    exports com.example.libraries.player;
    exports com.example.libraries.utils;

    // Si ces modules doivent être accessibles par FXML
    opens com.example.libraries.worldElements to javafx.fxml;
    opens com.example.libraries.factories to javafx.fxml;
    opens com.example.libraries.player to javafx.fxml;
}