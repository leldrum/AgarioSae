module com.example.agarioclientsae {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports agarioserversae.AI;
    opens agarioserversae.AI to javafx.fxml;
    exports agarioserversae.app;
    opens agarioserversae.app to javafx.fxml;
    exports agarioserversae.factories;
    opens agarioserversae.factories to javafx.fxml;
    exports agarioserversae.player;
    opens agarioserversae.player to javafx.fxml;
    exports agarioserversae.server;
    opens agarioserversae.server to javafx.fxml;
    exports agarioserversae.worldElements;
    opens agarioserversae.worldElements to javafx.fxml;
}